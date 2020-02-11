package com.example.ciphermania.Activity

import PlayFairCipher
import SubsitutionCipher
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.ciphermania.Ciphers.Cipher
import com.example.ciphermania.Ciphers.ShiftCipher
import com.example.ciphermania.Ciphers.VigenereCipher
import com.example.ciphermania.R
import com.example.ciphermania.databinding.ActivityMainBinding
import com.jaredrummler.materialspinner.MaterialSpinner

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        val list =
            listOf("Shift Cipher", "Vigenere Cipher", "Subsitution Cipher", "Playfair Cipher")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        binding.choiceSpinner.setAdapter(adapter)


        binding.submitBtn.setOnClickListener {
            //get choice of spinner
            Log.v("index", binding.choiceSpinner.selectedIndex.toString())

            val text = binding.plainText.text.toString()
            val key = binding.keyText.text.toString()

            if (TextUtils.isEmpty(text)) {
                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(key)) {
                Toast.makeText(this, "Enter key", Toast.LENGTH_SHORT).show()
            } else {
                val cipherObj =
                    Cipher(binding.plainText.text.toString(), binding.keyText.text.toString())
                //if shift cipher selected OR playfair all letters only
                if(binding.choiceSpinner.selectedIndex==1 ||binding.choiceSpinner.selectedIndex==3 ){
                    if(!checkKey()){
                        Toast.makeText(this, "Only alphabets are allowed in key", Toast.LENGTH_SHORT).show()
                        binding.outputTxt.text=""
                        return@setOnClickListener
                    }

                }

                when (binding.choiceSpinner.selectedIndex) {
                    0 -> if (binding.encryptChip.isChecked) {
                        val shiftCipher = ShiftCipher(cipherObj)
                        binding.outputTxt.text = "The encrypted value is ${shiftCipher.encrypt()}"
                    } else if (binding.decryptChip.isChecked) {
                        val shiftCipher = ShiftCipher(cipherObj)
                        binding.outputTxt.text = "The decrypted value is ${shiftCipher.decrypt()}"
                    }
                    1 -> if (binding.encryptChip.isChecked) {
                        val vigCipher = VigenereCipher(cipherObj)
                        binding.outputTxt.text = "The encrypted value is ${vigCipher.encrypt()}"
                    } else if (binding.decryptChip.isChecked) {
                        val vigCipher = VigenereCipher(cipherObj)
                        binding.outputTxt.text = "The decrypted value is ${vigCipher.decrypt()}"
                    }
                    2 -> if (binding.encryptChip.isChecked) {
                        val substCipher = SubsitutionCipher(cipherObj)
                        binding.outputTxt.text = "The encrypted value is ${substCipher.encrypt()}"
                    } else if (binding.decryptChip.isChecked) {
                        val substCipher = SubsitutionCipher(cipherObj)
                        binding.outputTxt.text = "The decrypted value is ${substCipher.decrypt()}"
                    }
                    else -> if (binding.encryptChip.isChecked) {
                        val playfairCipher = PlayFairCipher(cipherObj)
                        binding.outputTxt.text =
                            "The encrypted value is ${playfairCipher.encrypt()}"
                    } else if (binding.decryptChip.isChecked) {
                        val playfairCipher = PlayFairCipher(cipherObj)
                        binding.outputTxt.text =
                            "The decrypted value is ${playfairCipher.decrypt()}"
                    }
                }
//                if(binding.choiceSpinner.selectedIndex==0){
//                    if(binding.encryptChip.isChecked){
//                        val shiftCipher=ShiftCipher(cipherObj)
//                        binding.outputTxt.text="The encrypted value is ${shiftCipher.encrypt()}"
//                    }
//                    else if (binding.decryptChip.isChecked){
//                        val shiftCipher=ShiftCipher(cipherObj)
//                        binding.outputTxt.text="The decrypted value is ${shiftCipher.decrypt()}"
//                    }
//                }
//                else if(){
//                    if(binding.encryptChip.isChecked){
//
//                    }
//                    else if (binding.decryptChip.isChecked){
//
//                    }
//                }
                //if vigenere selected


            }

        }


        binding.choiceSpinner.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener { view, position, id, item: Any ->

            if(position==2) {
                binding.keyInputLayout3.isVisible = false
            }
            else{
                binding.keyInputLayout3.isVisible = true
                when (position) {
                    0 -> {
                        binding.keyText.inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    else -> {
                        binding.keyText.inputType = InputType.TYPE_CLASS_TEXT
                        //binding.keyText.keyListener=DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ")
                    }
                }

            }


        })

    }

    private fun checkKey():Boolean {
        return binding.keyText.text.toString().matches(Regex("^[a-zA-Z]*$"))
    }

}
