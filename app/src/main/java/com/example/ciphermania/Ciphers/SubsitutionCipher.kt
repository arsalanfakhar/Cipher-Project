

import com.example.ciphermania.Ciphers.Cipher
import com.example.ciphermania.Ciphers.CipherFunctions
import kotlin.math.absoluteValue

//fun main(){
//    val sc=SubsitutionCipher(Cipher(" never give upas asshole  ","ubest"))
//
//    println(sc.encrypt())
//
//    val dc=SubsitutionCipher(Cipher(sc.encrypt(),"ubest"))
//
//    println(dc.decrypt())
//
//}

class SubsitutionCipher(private val cipher: Cipher): CipherFunctions {

    //map to z to a
    private var subsitutionMap= hashMapOf<Char,Char>()
    private val listOfAlpha= arrayListOf<Char>()


    init {

        //list to intialize of Array
        listOfAlpha.addAll('a'..'z')

        //initialize hashmap
        //value z to a
        for(element in listOfAlpha){
            val mapVal=(listOfAlpha.indexOf(element)-25).absoluteValue
            val mapChar= listOfAlpha[mapVal]
            subsitutionMap.put(element,mapChar)
        }

//        println(subsitutionMap)
        cipher.plainText=cipher.plainText.replace("\\s".toRegex(), "").toLowerCase()
    }

    override fun encrypt(): String {
        //Verify no numbers are present


        var cipherText =""

        for (element in cipher.plainText){
            val cypherChar= subsitutionMap[element]

            cipherText+=cypherChar
        }
        return cipherText
    }

    override fun decrypt(): String {
        var plainText =""

        for (element in cipher.plainText){
            val plainChar= subsitutionMap[element]

            plainText+=plainChar
        }
        return plainText
    }

}