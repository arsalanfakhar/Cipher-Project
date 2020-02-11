package com.example.ciphermania.Ciphers



class VigenereCipher (private val cipher:Cipher):CipherFunctions{

    private val listOfAlpha= arrayListOf<Char>()

    init {
        //list to intialize of Array
        listOfAlpha.addAll('a'..'z')

        //Trim the text
        cipher.plainText=cipher.plainText.replace("\\s".toRegex(), "")

        val cipherLength=cipher.plainText.length
        val keyLength=cipher.key.length
        //Check length
        if(cipherLength!=keyLength){
            //as length is not equal concat the key

            val concatTimes=cipherLength.div(keyLength)

            cipher.key=cipher.key.repeat(concatTimes)

            //now concat the remainder
            val concatRem=cipherLength.rem(keyLength)
            if(concatRem!=0){
                val substr=cipher.key.substring(0,concatRem)
                cipher.key+=substr
            }

        }
    }

    override fun encrypt(): String {
        //now work for encryption
        var cipherText =""

        for(selectedChar in 0 until (cipher.plainText.length)){
            val ptVal=listOfAlpha.indexOf(cipher.plainText[selectedChar])
            val keyVal=listOfAlpha.indexOf(cipher.key[selectedChar])

            //Formula (Pi+Ki) mod26
            val cypherVal=(ptVal+keyVal).rem(listOfAlpha.size)
            val cypherChar=listOfAlpha[cypherVal]

            cipherText+=cypherChar
        }


        return cipherText
    }

    override fun decrypt(): String {
        //now work for encryption
        var plainText =""

        for(selectedChar in 0 until (cipher.plainText.length)){
            val ctVal=listOfAlpha.indexOf(cipher.plainText[selectedChar])
            val keyVal=listOfAlpha.indexOf(cipher.key[selectedChar])

            //Formula (Ci+Ki) mod26
            var plainVal=(ctVal-keyVal)
            if(plainVal<0){
                plainVal+=listOfAlpha.size
            }
            else{plainVal =plainVal.rem(listOfAlpha.size)}


            val plainChar=listOfAlpha[plainVal]

            plainText+=plainChar
        }


        return plainText
    }
}