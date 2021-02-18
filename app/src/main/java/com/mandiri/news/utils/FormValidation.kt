package com.mandiri.news.utils

import android.text.InputType
import android.util.Log
import android.util.Patterns
import android.widget.EditText

object FormValidation {
    fun validate(edits: List<EditText>): Boolean {
        var status = true
        for (i in edits) {
            if (i.text.toString().isEmpty()) {
                status = false
                i.error = "Masukkan ${i.hint}"
                    Log.i("autolog", "Harap isi ${i.hint}: ")
                i.isFocusable =true
                i.requestFocus()
                break
            }else{
                when(i.inputType){
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or 33 ->{
                        if (!Patterns.EMAIL_ADDRESS.matcher(i.text.toString()).matches()){
                            Log.i("autolog", "Masukkan ${i.hint}: ")
                            status = false
                            i.error="Format email tidak sesuai"
                            i.isFocusable = true
                            i.requestFocus()
                        }
                    }
                    InputType.TYPE_TEXT_VARIATION_PHONETIC ->{
                        if (i.length()<7){
                            Log.i("autolog", "Masukkan ${i.hint}: ")
                            status = false
                            i.error="Nomor telepon terlalu pendek"
                            i.isFocusable = true
                            i.requestFocus()
                        }
                    }
                }
            }
        }
        return status
    }
}