
import com.example.ciphermania.Ciphers.Cipher
import com.example.ciphermania.Ciphers.CipherFunctions


enum class PlayfairOption {
    NO_Q,
    I_EQUALS_J
}

class PlayFairCipher(
    private val cipher: Cipher,
    val pfo: PlayfairOption = PlayfairOption.I_EQUALS_J
) : CipherFunctions {

    private val table: Array<CharArray> = Array(5) { CharArray(5) }  // 5 x 5 char array

    init {
        // build table
        val used = BooleanArray(26)  // all elements false
        if (pfo == PlayfairOption.NO_Q)
            used[16] = true  // Q used
        else
            used[9] = true  // J used
        val alphabet = cipher.key.toUpperCase() + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var i = 0
        var j = 0
        var c: Char
        var d: Int

        //Initialize the matrix
        for (k in 0 until alphabet.length) {
            c = alphabet[k]
            if (c !in 'A'..'Z') continue
            d = c.toInt() - 65
            if (!used[d]) {
                table[i][j] = c
                used[d] = true
                if (++j == 5) {
                    if (++i == 5) break // table has been filled
                    j = 0
                }
            }
        }
//        printTable()
    }

    private fun getCleanText(plainText: String): String {
        val plainText2 = plainText.toUpperCase()  // ensure everything is upper case
        // get rid of any non-letters and insert X between duplicate letters
        var cleanText = ""
        var prevChar = '\u0000'  // safe to assume null character won't be present in plainText
        var nextChar: Char
        for (i in 0 until plainText2.length) {
            nextChar = plainText2[i]
            // It appears that Q should be omitted altogether if NO_Q option is specified - we assume so anyway
            if (nextChar !in 'A'..'Z' || (nextChar == 'Q' && pfo == PlayfairOption.NO_Q)) continue
            // If I_EQUALS_J option specified, replace J with I
            if (nextChar == 'J' && pfo == PlayfairOption.I_EQUALS_J) nextChar = 'I'
            if (nextChar != prevChar)
                cleanText += nextChar
            else
                cleanText += "X" + nextChar
            prevChar = nextChar
        }
        val len = cleanText.length
        if (len % 2 == 1) {  // dangling letter at end so add another letter to complete digram
            if (cleanText[len - 1] != 'X')
                cleanText += 'X'
            else
                cleanText += 'Z'
        }
        return cleanText
    }

    private fun findChar(c: Char): Pair<Int, Int> {
        for (i in 0..4)
            for (j in 0..4)
                if (table[i][j] == c) return Pair(i, j)
        return Pair(-1, -1)
    }

//    private fun printTable() {
//        println("The table to be used is :\n")
//        for (i in 0..4) {
//            for (j in 0..4) print(table[i][j] + " ")
//            println()
//        }
//    }

    override fun encrypt(): String {
        val cleanText = getCleanText(cipher.plainText)
        var cipherText = ""
        val length = cleanText.length
        for (i in 0 until length step 2) {
            val (row1, col1) = findChar(cleanText[i])
            val (row2, col2) = findChar(cleanText[i + 1])
            cipherText += when {
                row1 == row2 -> table[row1][(col1 + 1) % 5].toString() + table[row2][(col2 + 1) % 5]
                col1 == col2 -> table[(row1 + 1) % 5][col1].toString() + table[(row2 + 1) % 5][col2]
                else -> table[row1][col2].toString() + table[row2][col1]
            }
            if (i < length - 1) cipherText += " "
        }
        return cipherText
    }

    override fun decrypt(): String {
        var cipherText=cipher.plainText.toUpperCase()


//        //Separate string by spaces
//        for(i in cipher.plainText.indices step 2){
//            val sub=cipher.plainText.substring(i,i+2)
//            cipherText+= "$sub "
//        }

        var decodedText = ""
        val length = cipherText.length
        for (i in 0 until length step 3) {  // cipherText will include spaces so we need to skip them
            val (row1, col1) = findChar(cipherText[i])
            val (row2, col2) = findChar(cipherText[i + 1])
            decodedText += when {
                row1 == row2 -> table[row1][if (col1 > 0) col1 - 1 else 4].toString() + table[row2][if (col2 > 0) col2 - 1 else 4]
                col1 == col2 -> table[if (row1 > 0) row1- 1 else 4][col1].toString() + table[if (row2 > 0) row2 - 1 else 4][col2]
                else         -> table[row1][col2].toString() + table[row2][col1]
            }
            if (i < length - 1) decodedText += " "
        }
        //remove white spaces
        return decodedText.replace("\\s".toRegex(), "")


    }

}
//
//fun main() {
//    print("Enter Playfair keyword : ")
//    val keyword: String = readLine()!!
//
//    print("Enter plain text : ")
//    val plainText: String = readLine()!!
//
//
//    val playfair = PlayFairCipher(Cipher(plainText = plainText,key = keyword))
//
//
//    val encodedText = playfair.encrypt()
//    println("\nEncoded text is : $encodedText")
//
//    val playfair1 = PlayFairCipher(Cipher(plainText = encodedText,key = keyword))
//
//    val decodedText = playfair1.decrypt()
//    println("Decoded text is : $decodedText")
//
//}