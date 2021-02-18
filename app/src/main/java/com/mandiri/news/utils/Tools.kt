package com.mandiri.news.utils

import android.widget.EditText
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and


object Tools {

    val dateIndo: String = "dd-MM-yyyy"
    val dateOnly: String = "dd"
    val timeIndo: String = "HH:mm"
    val dateTimeIndo: String = dateIndo + " " + timeIndo
    val dateIndoLengkap: String = "dd-MM-yyyy HH:mm:ss"
    val dateIndoLengkapS: String = "dd/MM/yyyy HH:mm:ss"

    fun tahunOnly(): Int = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()
    fun dateIndo(): String = SimpleDateFormat(dateIndo, Locale.getDefault()).format(Date())
    fun timesIndo(): String = SimpleDateFormat(timeIndo, Locale.getDefault()).format(Date())
    fun dateOnly(): String = SimpleDateFormat(dateOnly, Locale.getDefault()).format(Date())
    fun dateIndoLengkap(): String = SimpleDateFormat(dateIndoLengkap, Locale.getDefault()).format(Date())
    fun dateIndoLengkapS(): String = SimpleDateFormat(dateIndoLengkapS, Locale.US).format(Date())
    fun dateTimeCustom(string: String): String = SimpleDateFormat(string, Locale.US).format(Date())


    fun currencyBanyak(s: String): String = DecimalFormat("###,###,###,###,###")
        .format(s.toDouble()).replace(",", ".")

    fun changeDateFormat(input: String, oldFormat: String, newFormat: String): String {
        val date = input
        val inputs = SimpleDateFormat(oldFormat, Locale.US)
        val output = SimpleDateFormat(newFormat, Locale.US)/*
        val inputs = SimpleDateFormat(oldFormat, Locale.getDefault())
        val output = SimpleDateFormat(newFormat, Locale.getDefault())*/
        try {
            val parseDate = inputs.parse(date)
            val newDate = output.format(parseDate)
            return newDate
        } catch (e: ParseException) {
            return ""
        }
    }

    fun clearEditText(listE: List<EditText>) {
        listE.forEach {
            it.setText("")
        }
        listE[0].isFocusable = true
    }

    fun hash256(data: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(data.toByteArray())
        return bytesToHex(md.digest())
    }

    fun bytesToHex(bytes: ByteArray): String {
        val result = StringBuffer()
        for (byt in bytes) result.append(Integer.toString((byt and 0xff.toByte()) + 0x100, 16).substring(1))
        return result.toString()
    }
}

fun getDateTimeAppointment(date:String) = Tools.changeDateFormat(date, "dd-MMM-yy", "dd MMMM YYYY")
fun getDateTimeAppointment2(date:String) = Tools.changeDateFormat(date, "dd-MMM-yy hh.mm.ss", "dd MMMM YYYY hh:mm")
fun getTimeAppointment(date:String) = Tools.changeDateFormat(date, "dd-MMM-yy hh.mm.ss", "hh:mm")
fun getDateAppointment(date:String) = Tools.changeDateFormat(date, "dd-MMM-yy hh.mm.ss", "dd MMMM YYYY")
fun getDateTimeAppointmentUp(date:String) = Tools.changeDateFormat(date, "dd-MMM-yy", "dd MMMM YYYY")
