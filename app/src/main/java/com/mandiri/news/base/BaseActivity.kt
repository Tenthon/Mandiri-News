package com.mandiri.news.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.mandiri.news.R
import com.mandiri.news.data.local.SessionPref
import kotlinx.android.synthetic.main.toolbar_transparant.*

open class BaseActivity : AppCompatActivity() {
    lateinit var sessionPref: SessionPref
    val SUCCESS = "sukses"
    val ERROR = "error"
    var pDialog: SweetAlertDialog? = null

    fun toast(message: String) = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    fun toastLong(message: String) = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    fun snack(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

    fun goto(classes: Any) = startActivity(Intent(this, classes as Class<*>))

    fun gotoWithClose(classes: Any) {
        startActivity(Intent(this, classes as Class<*>))
        this.finish()
    }
    fun gotoWithParam(classes: Any, name: List<String>, value: List<String>) {
        val intent=Intent(this, classes as Class<*>)
        for (i in name.withIndex()){
            intent.putExtra(i.value, value[i.index])
        }
        startActivity(intent)
    }

    fun gotoWithClearClose(classes: Any) {
        finish()
        Intent(this, classes as Class<*>).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    fun whiteStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }

    fun setToolbar() {
        btnBack.setOnClickListener { this.finish() }
    }

    fun showLoading(message: String) {
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.contentText = "$message"
        pDialog?.setCancelable(false)
        pDialog?.show()
    }

    protected fun initRecycler(recycler: RecyclerView?) {
        val mManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    protected fun initRecyclerGrid(recycler: RecyclerView?) {
        val mManager = GridLayoutManager(this, 2)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    fun getSession() {
        sessionPref = SessionPref.instance(this)
    }

    fun toast(type: String, message: String) {
        val res = R.layout.custom_toast_small
        val li = layoutInflater
        val layout = li.inflate(res, null)
        val text = layout.findViewById<TextView>(R.id.textToast)
        val linearToast = layout.findViewById<LinearLayout>(R.id.linearToast)
        if (type.equals(SUCCESS)) linearToast.setBackgroundResource(R.color.colorPrimary)
        text.text = message
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM, 0, 103)
        toast.view = layout
        toast.show()
    }

    fun showDialogRounded(
        layout: Int,
        isCancel: Boolean?,
        onShow: (dialogs: AlertDialog, views: View) -> Unit
    ) {
        val dialog: AlertDialog
        val dialogBuilder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(layout, null)
        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()
        dialog.setCancelable(isCancel ?: true)
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_shape_white)
        dialog.show()
        onShow(dialog, view)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }
}
