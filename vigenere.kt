import java.lang.StringBuilder
import kotlin.reflect.KFunction2
import kotlin.reflect.KFunction3

const val primeiraLetra = 65
const val primeiraLetraVisivel = 97
const val chave = "iamie xistt hatis cert"
const val mensagem = "MACHI NESCA NNOTT HINK"

fun main(args: Array<String>) {
    val (chaveOpcional, mensagemOpcional) = lerInputChaveMensagem()
    println("Chave que será utilizada será \\$chaveOpcional\\ e mensagem que será utilizada será \\$mensagemOpcional\\")
    val faixaLetras = 65..90
    val n = faixaLetras.count()
    val tempoInicial = System.currentTimeMillis()
    val mensagemCifrada = fazAMagica(chaveOpcional, mensagemOpcional, n, ::encode)
    val mensagemDecifrada = fazAMagica(chaveOpcional, mensagemCifrada, n, ::decode)
    println("Sua mensagem cifrada é \t\t$mensagemCifrada")
    println("Sua mensagem decifrada é \t$mensagemDecifrada")
    println("Tempo decorrido foi de ${System.currentTimeMillis().minus(tempoInicial)} ms")
    assert(mensagemOpcional.equals(mensagemDecifrada, ignoreCase = true))
}

private fun lerInputChaveMensagem(): Pair<String, String> {
    println("Coloque sua chave e sua mensagem separadas por quebra de linha ou deixe em branco para utilizar valores padrões")
    val chaveLida = readLine()
    val mensagemLida = readLine()
    return if (!(chaveLida.isNullOrEmpty() || mensagemLida.isNullOrEmpty()))
        Pair(chaveLida, mensagemLida)
    else if (chaveLida.isNullOrEmpty() && !mensagemLida.isNullOrEmpty())
        Pair(chave, mensagemLida)
    else if (!chaveLida.isNullOrEmpty() && mensagemLida.isNullOrEmpty())
        Pair(chaveLida, mensagem)
    else
        Pair(chave, mensagem)
}

fun fazAMagica(chave: String, mensagem: String, n: Int, funcao: KFunction3<Int, Int, Int, Char>): String {
    val mensagemCifrada = StringBuilder()
    val mensagemUp = mensagem.toUpperCase()
    val chaveUp = chave.toUpperCase()
    for ((i, c) in mensagemUp.withIndex()) {
        val letraAdicionada = if (!c.isWhitespace())
            funcao.invoke(c.toInt(), chaveUp.elementAt(i.rem(chaveUp.length)).toInt(), n)
        else
            c
        mensagemCifrada.append(letraAdicionada)
    }
    return mensagemCifrada.toString()
}

fun encode(letraMensagem: Int, letraChave: Int, n: Int) =
    operacaoAlgebrica(letraMensagem, letraChave, n, Int::plus)

fun decode(letraMensagem: Int, letraChave: Int, n: Int) =
    operacaoAlgebrica(letraMensagem.plus(n), letraChave, n, Int::minus)

fun operacaoAlgebrica(letraMensagem: Int, letraChave: Int, n: Int, param: KFunction2<Int, Int, Int>): Char =
    param.invoke(letraMensagem.minus(primeiraLetra), (letraChave.minus(primeiraLetra))).rem(n)
        .plus(primeiraLetraVisivel).toChar()