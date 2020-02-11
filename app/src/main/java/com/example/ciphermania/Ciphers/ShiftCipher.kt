package com.example.ciphermania.Ciphers


class ShiftCipher(private val cipher:Cipher):CipherFunctions{

    private val listOfAlpha= arrayListOf<Char>()

    init {
        //list to intialize of Array
        listOfAlpha.addAll('a'..'z')
    }

    override fun encrypt():String {
        //Verify no numbers are present
        val textArr=cipher.plainText.replace("\\s".toRegex(), "").toLowerCase().toCharArray()

        var cipherText =""

        val key=cipher.key.toInt()
        for (element in textArr){
            //get index of the value
            //(x+n) mod n
            val cypherVal= (listOfAlpha.indexOf(element)+key).rem(listOfAlpha.size)


            val cypherChar= listOfAlpha[cypherVal]

            cipherText+=cypherChar

        }
        return cipherText
    }

    override fun decrypt():String {
        //Verify no numbers are present
        val textArr=cipher.plainText.replace("\\s".toRegex(), "").toLowerCase().toCharArray()

        var cipherText =""

        val key=cipher.key.toInt()
        for (element in textArr){
            //get index of the value
            //(x+n) mod n
            var cypherVal= (listOfAlpha.indexOf(element)-key)

            if(cypherVal<0){
                //in negative then add
                cypherVal += listOfAlpha.size
            }
            else{cypherVal=cypherVal.rem(listOfAlpha.size)}


            val cypherChar= listOfAlpha[cypherVal]

            cipherText+=cypherChar

        }
        return cipherText
    }

}