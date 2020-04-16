import java.util.Random
import java.io.*

fun menuBatalhaNaval(){
    println()
    println("*** BATALHA NAVAL ***")
    println()
}

fun processaNomeJogador(texto: String): String? {
    var nome = ""
    var count = 0
    var dadosInvalidos = false
    if (dadosInvalidos == false ){
        if (texto.isEmpty()) {
            dadosInvalidos = true
        } else if (texto == "-1") {
            dadosInvalidos = false
        } else if (dadosInvalidos == false){
            if (texto[texto.length-1].isLetter() && texto[texto.length-1].isUpperCase()){
                dadosInvalidos=true
            }
            for (posicao in 0..texto.length-1){
                if ((posicao>0 && texto[posicao]==' ' && texto[posicao-1]==' ') || (posicao==0 && texto[posicao]==' ')){
                    dadosInvalidos = true
                }
                if (posicao==0 || texto[posicao-1]==' '){
                    if (!(texto[posicao].isLetter() && texto[posicao].isUpperCase())){
                        dadosInvalidos = true
                    }
                } else if (!((texto[posicao].isLetter() && texto[posicao].isLowerCase()) || texto[posicao]==' ')){
                    dadosInvalidos = true
                }
            }
        }
        while (count<texto.length && texto[count]!=' '){
            nome = "${nome}${texto[count]}"
            count++
        }
    }
    if (dadosInvalidos == true){
        return null
    } else {
        return nome
    }
}

fun insereNavio(tabuleiro: Array<Char?>, posicaoNavio: Int, dimensaoNavio: Int): Boolean{
    var dadosInvalidos = true
    if (posicaoNavio==0) {
        for (espacos in posicaoNavio+1..posicaoNavio+dimensaoNavio-1){
            if (tabuleiro[espacos]!=null){
                dadosInvalidos = true
            }
        }
    } else if (posicaoNavio>0){
        for (espacos in posicaoNavio-1..posicaoNavio+dimensaoNavio-1) {
            if (espacos<tabuleiro.size && tabuleiro[espacos] == null) {
                dadosInvalidos = false
            } else {
                dadosInvalidos = true
            }
        }
    } else if (posicaoNavio+dimensaoNavio-1==tabuleiro.size) {
        for (espacos in posicaoNavio-1.. tabuleiro.size-1){
            if (tabuleiro[espacos]==null){
                dadosInvalidos=false
            }
        }

    }

    if (dadosInvalidos==true){
        return false
    } else {
        return true
    }
}


fun geraMapaComputador(tabuleiro: Array<Char?>, numNaviosTanque: Int, numContratorpedeiros: Int, numSubmarinos: Int){
    var dadosInvalidos = false
    var count = 0
    do{
        val posicao = Random().nextInt(tabuleiro.size)
        val posValida = insereNavio(tabuleiro, posicao ,3)
        if (posValida == true){
            dadosInvalidos=false
            for (umaPosicao in posicao..(posicao+3)-1) {
                tabuleiro[umaPosicao] = '3'
            }
            count++
        } else {
            dadosInvalidos = true
        }
    } while (dadosInvalidos==true || count<numNaviosTanque)
    count = 0
    do{
        val posicao = Random().nextInt(tabuleiro.size)
        val posValida = insereNavio(tabuleiro, posicao ,2)
        if (posValida == true){
            dadosInvalidos=false
            for (umaPosicao in posicao..(posicao+2)-1) {
                tabuleiro[umaPosicao] = '2'
            }
            count++
        } else {
            dadosInvalidos = true
        }
    } while (dadosInvalidos==true || count<numContratorpedeiros)
    count = 0
    do{
        val posicao = Random().nextInt(tabuleiro.size)
        val posValida = insereNavio(tabuleiro, posicao ,1)
        if (posValida == true){
            dadosInvalidos=false
            for (umaPosicao in posicao..(posicao+1)-1) {
                tabuleiro[umaPosicao] = '1'
            }
            count++
        } else {
            dadosInvalidos = true
        }
    } while (dadosInvalidos==true || count<numSubmarinos)

}

fun imprimirMapa(tabuleiro: Array<Char?>, real: Boolean){
    println(obtemMapa(tabuleiro, real)[0])
    println(obtemMapa(tabuleiro, real)[1])
    println(obtemMapa(tabuleiro, real)[2])
}

fun obtemMapa(tabuleiro: Array<Char?>, real: Boolean) : Array<String>{
    var linhaMar = ""
    if (real == true) {
        for (posicao in 0..tabuleiro.size-1){
            if (tabuleiro[posicao] == null) {
                linhaMar = "${linhaMar}~"
            }
            if (tabuleiro[posicao]=='1'){
                linhaMar = "${linhaMar}1"
            }
            if (tabuleiro[posicao]=='2'){
                linhaMar = "${linhaMar}2"
            }
            if (tabuleiro[posicao]=='3'){
                linhaMar = "${linhaMar}3"
            }
        }
    }

    if (real == false) {
        for (posicao in 0..tabuleiro.size-1){
            var adicionarTresGrande=false
            if (tabuleiro[posicao] == null) {
                linhaMar = "${linhaMar}?"
            }
            if (tabuleiro[posicao]=='1'){
                linhaMar = "${linhaMar}1"
            }
            if (tabuleiro[posicao]=='2'){
                if ((posicao>0 && tabuleiro[posicao-1]=='2') || (posicao<tabuleiro.size && tabuleiro[posicao+1]=='2')) {
                    linhaMar = "${linhaMar}2"
                } else {
                    linhaMar = "${linhaMar}\u2082"
                }
            }
            if (tabuleiro[posicao]=='3') {
                if ((posicao > 1 && tabuleiro[posicao - 1] == '3' && tabuleiro[posicao - 2] == '3')) {
                    adicionarTresGrande = true
                } else if (posicao < (tabuleiro.size - 1) && tabuleiro[posicao + 2] == '3' && tabuleiro[posicao + 1] == '3') {
                    adicionarTresGrande = true
                } else if (posicao in 1..(tabuleiro.size - 1) && tabuleiro[posicao - 1] == '3' && tabuleiro[posicao + 1] == '3') {
                    adicionarTresGrande = true
                } else {
                    linhaMar = "${linhaMar}\u2083"
                }
            }
            if (adicionarTresGrande == true){
                linhaMar = "${linhaMar}3"
            }
            if (tabuleiro[posicao]=='X'){
                linhaMar = "${linhaMar}X"
            }
        }
    }
    var linhaNumeros: String = ""
    var linhaDezenas: String = ""
    var numeros = 1
    var dezenas = 0
    var sequencia = 0
    while (sequencia != tabuleiro.size) {
        sequencia++
        if (numeros == 10) {
            dezenas++
            numeros = 0
        }
        if (numeros < 10) {
            linhaNumeros= "${linhaNumeros}${numeros}"
            numeros++
        }
    }
    if (dezenas != 0) {
        var count = 0
        for (posicao in 0..tabuleiro.size){
            if (posicao!=0 && posicao%10==0){
                count++
                linhaDezenas = "${linhaDezenas}${count}"
            } else if (posicao==0){
                linhaDezenas = ""
            } else {
                linhaDezenas = "${linhaDezenas} "
            }
        }
    }
    return arrayOf(linhaMar, linhaNumeros, linhaDezenas)
}

fun calculaNumNavios(tamanhoTabuleiro: Int): Array<Int>{
    val numNavios = arrayOf(0,0,0)

    if (tamanhoTabuleiro<=30) {
        numNavios[0]=1
        numNavios[1]=1
        numNavios[2]=1
    } else if (tamanhoTabuleiro in 31..50) {
        numNavios[0]=1
        numNavios[1]=1
        numNavios[2]=3
    } else if (tamanhoTabuleiro in 51..80) {
        numNavios[0]=1
        numNavios[1]=2
        numNavios[2]=5
    } else if (tamanhoTabuleiro in 81..99) {
        numNavios[0]=2
        numNavios[1]=3
        numNavios[2]=6
    } else {
        numNavios[0]=-1
        numNavios[1]=-1
        numNavios[2]=-1
    }
    return numNavios
}

fun lancarTiro(tabuleiroReal: Array<Char?>, tabuleiroPalpites: Array<Char?>, posicaoTiro: Int): String{
    if (tabuleiroReal[posicaoTiro-1]=='1') {
        tabuleiroPalpites[posicaoTiro-1]='1'
        return "Tiro num submarino."
    } else if (tabuleiroReal[posicaoTiro-1]=='3') {
        tabuleiroPalpites[posicaoTiro-1]='3'
        return "Tiro num navio-tanque."
    } else if (tabuleiroReal[posicaoTiro-1]=='2') {
        tabuleiroPalpites[posicaoTiro - 1] = '2'
        return "Tiro num contratorpedeiro."
    } else {
        tabuleiroPalpites[posicaoTiro-1]='X'
        return "Agua."
    }
}

fun geraJogadaComputador(tabuleiroPalpites: Array<Char?>): Int{
    var tiro: Int = -1
    var tiroValido = false
    do {
        val posicao: Int = Random().nextInt(tabuleiroPalpites.size-1)
        if (tabuleiroPalpites[posicao]==null) {
            tiroValido = true
            tiro = posicao
            if (posicao>0 && tabuleiroPalpites[posicao-1]=='X' && posicao+1<tabuleiroPalpites.size-1 && !(tabuleiroPalpites[posicao+1]=='X' || tabuleiroPalpites[posicao+1]==null)) {
                tiroValido=false
            }
        } else {
            tiroValido=false
        }
    } while (tiroValido==false)

    return tiro
}

fun venceu(tabuleiroPalpites: Array<Char?>): Boolean{
    var venceuNavioTanque = false
    val numNaviosTanque=calculaNumNavios(tabuleiroPalpites.size)[0]*3
    var count=0
    for (posicao in 0..tabuleiroPalpites.size-1){
        if (tabuleiroPalpites[posicao]=='3'){
            count++
        }
    }
    if (count==numNaviosTanque){
        venceuNavioTanque=true
    }
    var venceuContratorpedeiros = false
    val numContratorpedeiros=calculaNumNavios(tabuleiroPalpites.size)[1]*2
    count=0
    for (posicao in 0..tabuleiroPalpites.size-1){
        if (tabuleiroPalpites[posicao]=='2'){
            count++
        }
    }
    if (count==numContratorpedeiros){
        venceuContratorpedeiros=true
    }
    var venceuSubmarinos = false
    val numSubmarinos=calculaNumNavios(tabuleiroPalpites.size)[2]
    count=0
    for (posicao in 0..tabuleiroPalpites.size-1){
        if (tabuleiroPalpites[posicao]=='1'){
            count++
        }
    }
    if (count==numSubmarinos){
        venceuSubmarinos=true
    }
    var venceu=false
    if (venceuNavioTanque == true && venceuContratorpedeiros == true && venceuSubmarinos == true){
        venceu = true
    }
    return venceu
}

fun leFicheiro (nomeFicheiro: String): Array<String> {
    val lines = File(nomeFicheiro).readLines()
    val linesArray = arrayOf("","","","","","","","","","","")
    for (line in lines) {
        val parts = line.split(':')
        linesArray[0] = parts[0]
        linesArray[1] = parts[1]
        linesArray[2] = parts[2]
        linesArray[3] = parts[3]
        linesArray[4] = parts[4]
        linesArray[5] = parts[5]
        linesArray[6] = parts[6]
        linesArray[7] = parts[7]
        linesArray[8] = parts[8]
        linesArray[9] = parts[9]
        linesArray[10] = parts[10]

    }
    return (linesArray)
}

fun leMapaDoFicheiro(nomeFicheiro: String, tipoMapa: Int): Array<Char?>{
    try {
        val mapa = arrayOfNulls<Char?>(leFicheiro(nomeFicheiro)[2].length-1)
        if (tipoMapa == 1) {
            val lineMapaRealDoJogador= leFicheiro(nomeFicheiro)[2]
            for (posicao in 0..mapa.size - 1) {
                if (lineMapaRealDoJogador[posicao] == '~') {
                    mapa[posicao] = null
                }
                if (lineMapaRealDoJogador[posicao] == '1') {
                    mapa[posicao] = '1'
                }
                if (lineMapaRealDoJogador[posicao] == '2') {
                    mapa[posicao] = '2'
                }
                if (lineMapaRealDoJogador[posicao] == '3') {
                    mapa[posicao] = '3'
                }
            }
        }
        if (tipoMapa == 2) {

        }
        return mapa
    } catch (e: FileNotFoundException) {
        return emptyArray()
    }
}

fun leNomeJogadorDoFicheiro(nomeFicheiro: String): String{
    return "test"
}

fun gravaJogoEmFicheiro(nomeFicheiro: String, nomeJogador: String, tabuleiroRealHumano: Array<Char?>, tabuleiroPalpitesHumano: Array<Char?>, tabuleiroRealComputador: Array<Char?>, tabuleiroPalpitesComputador: Array<Char?>){

}

fun calculaEstatisticas(tabuleiroPalpites: Array<Char?>): Array<Int>{
    var numNaviosTanqueAfundados: Int = 0
    var numContratorpedeirosAfundados = 0
    var numSubmarinosAfundados = 0
    var numJogadasEfectuadas = 0
    var precisaoTiro = 0
    var numTirosCerteiros = 0
    var numNaviosAfundados = 0
    for (posicao in 0..tabuleiroPalpites.size-1){
        if (tabuleiroPalpites[posicao]=='3'){
            numNaviosTanqueAfundados++
        }
        if (tabuleiroPalpites[posicao]=='2'){
            numContratorpedeirosAfundados++
        }
        if (tabuleiroPalpites[posicao]=='1'){
            numSubmarinosAfundados++
        }
        if (tabuleiroPalpites[posicao]!='X' && tabuleiroPalpites[posicao]!=null){
            numTirosCerteiros++
        }
        if (tabuleiroPalpites[posicao]!=null) {
            numJogadasEfectuadas++
        }
    }


    numNaviosTanqueAfundados = (numNaviosTanqueAfundados/3)
    numContratorpedeirosAfundados = (numContratorpedeirosAfundados/2)
    if (numJogadasEfectuadas==0){
        precisaoTiro=0
    }else {
        precisaoTiro = (100*numTirosCerteiros)/numJogadasEfectuadas.toInt()
    }
    numNaviosAfundados = numNaviosTanqueAfundados+numContratorpedeirosAfundados+numSubmarinosAfundados
    return arrayOf(numNaviosTanqueAfundados, numContratorpedeirosAfundados, numSubmarinosAfundados, numJogadasEfectuadas, precisaoTiro, numTirosCerteiros, numNaviosAfundados)
}

fun calculaNaviosFaltaAfundar (tabuleiroPalpites: Array<Char?>): Array<Int> {
    var numSubmarinosAfundados = 0
    var numContratorpedeirosAfundados = 0
    var numNaviosTanqueAfundados = 0
    for (posicao in 0..tabuleiroPalpites.size-1) {
        if (tabuleiroPalpites[posicao]=='1') {
            numSubmarinosAfundados++
        }
        if (tabuleiroPalpites[posicao]=='2') {
            if (posicao+1<tabuleiroPalpites.size && tabuleiroPalpites[posicao+1]=='2'){
                numContratorpedeirosAfundados++
            }
        }
        if (tabuleiroPalpites[posicao]=='3') {
            if (posicao+2<tabuleiroPalpites.size && tabuleiroPalpites[posicao+1]=='3' && tabuleiroPalpites[posicao+2]=='3'){
                numNaviosTanqueAfundados++
            }
        }
    }
    val numNaviosTanque = calculaNumNavios(tabuleiroPalpites.size)[0]-numNaviosTanqueAfundados
    val numContratorpedeiros = calculaNumNavios(tabuleiroPalpites.size)[1]-numContratorpedeirosAfundados
    val numSubmarinos = calculaNumNavios(tabuleiroPalpites.size)[2]-numSubmarinosAfundados
    return arrayOf(numNaviosTanque, numContratorpedeiros, numSubmarinos)
}

fun main (args: Array<String>){
    do{
        val dadosDeEntradaInvalidos = ">>> Dados de entrada invalidos, tente novamente"
        menuBatalhaNaval()
        println("1 - Definir mapa Humano vs Computador")
        println("2 - Iniciar jogo")
        println("3 - Gravar ficheiro")
        println("4 - Ler ficheiro")
        println("5 - Estatisticas")
        println("0 - Sair")
        println()
        var opcaoMenuPrincipal = -1
        var queroVoltarAoInicio = false
        var dadosInvalidos: Boolean
        do {
            val opcao = readLine()!!.toIntOrNull()
            if (opcao== null || !(opcao ==-1 || opcao in 0..5)){
                dadosInvalidos=true
                println(dadosDeEntradaInvalidos)
            } else {
                dadosInvalidos=false
                opcaoMenuPrincipal=opcao
            }
        } while (dadosInvalidos==true)
        if (opcaoMenuPrincipal==1) {
            menuBatalhaNaval()
            println("Escolha o tamanho do tabuleiro:")
            var tamanhoTabuleiro: Int = 0
            if (!queroVoltarAoInicio) {
                do {
                    val tamanho: Int? = readLine()!!.toIntOrNull()
                    if (tamanho != null && (tamanho == -1)) {
                        dadosInvalidos = false
                        queroVoltarAoInicio = true
                    } else if (tamanho != null && !(tamanho in 10..99)) {
                        dadosInvalidos = true
                        println(">>> Tamanho de tabuleiro invalido, tente novamente")
                    } else if (tamanho == null) {
                        dadosInvalidos = true
                        println(dadosDeEntradaInvalidos)
                    } else {
                        dadosInvalidos = false
                        tamanhoTabuleiro = tamanho
                    }
                } while (dadosInvalidos == true)
            }
            var nomeJogador: String = ""
            if (!queroVoltarAoInicio){
                println("Introduza o nome do jogador(a):")
                do{
                    nomeJogador = readLine().orEmpty()
                    val nome= processaNomeJogador(nomeJogador)
                    if (nome=="-1"){
                        dadosInvalidos = false
                        queroVoltarAoInicio = true
                    } else if (nome == null) {
                        dadosInvalidos = true
                        println(">>> Nome invalido, tente novamente")
                    } else {
                        dadosInvalidos = false
                        nomeJogador = nome
                    }
                } while (dadosInvalidos==true)
            }
            val tabuleiro = arrayOfNulls<Char?>(tamanhoTabuleiro)
            if (!queroVoltarAoInicio){
                menuBatalhaNaval()
                println("Mapa do ${nomeJogador}:")
                imprimirMapa(tabuleiro, true)
                var count=0
                do {
                    println("${nomeJogador}, introduz a posicao para 1 navio-tanque:")
                    var navio: Int?
                    do {
                        navio = readLine()!!.toIntOrNull()
                        var posicaoNavio: Int
                        if (navio != null && navio == -1) {
                            dadosInvalidos = false
                            queroVoltarAoInicio = true
                        } else if (navio == null || !(navio in 1..tamanhoTabuleiro)) {
                            dadosInvalidos = true
                            println(dadosDeEntradaInvalidos)
                        } else {
                            posicaoNavio = navio-1
                            val insereNavio = insereNavio(tabuleiro, posicaoNavio, 1)
                            if (insereNavio == false) {
                                dadosInvalidos = true
                                println(dadosDeEntradaInvalidos)
                            } else {
                                dadosInvalidos = false
                                for (posicao in posicaoNavio..(posicaoNavio+3)-1) {
                                    tabuleiro[posicao] = '3'
                                }
                                println()
                                imprimirMapa(tabuleiro, true)
                                count++
                            }
                        }
                    } while (dadosInvalidos==true)
                } while (queroVoltarAoInicio==false && count<calculaNumNavios(tamanhoTabuleiro)[0])
            }
            if (!queroVoltarAoInicio){

            }
        }

    } while (opcaoMenuPrincipal!=0)
}