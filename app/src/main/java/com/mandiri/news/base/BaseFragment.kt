package com.mandiri.news.base

import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.mandiri.news.R
import com.mandiri.news.data.local.SessionPref
import kotlinx.android.synthetic.main.dialog_default.view.*

open class BaseFragment : Fragment() {
    lateinit var sessionPref: SessionPref
    val SUCCESS = "sukses"
    val ERROR = "error"
    var pDialog: SweetAlertDialog? = null

    fun toast(message: String) = Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    fun snack(view: View, message: String) = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    fun goto(classes: Any) = startActivity(Intent(activity, classes as Class<*>))

    fun gotoWithClose(classes: Any) {
        startActivity(Intent(activity, classes as Class<*>))
        activity!!.finish()
    }

    protected fun initRecycler(recycler: RecyclerView?) {
        val mManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler!!.apply {
            layoutManager = mManager
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    fun showLoading(message: String) {
        pDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.contentText = "$message"
        pDialog?.show()
    }

    fun getSession() {
        sessionPref = SessionPref.instance(activity!!)
    }

    fun toast(type: String, message: String) {
        val res = R.layout.custom_toast_small
        val li = layoutInflater
        val layout = li.inflate(res, null)
        val text = layout.findViewById<TextView>(R.id.textToast)
        val linearToast = layout.findViewById<LinearLayout>(R.id.linearToast)
        if (type.equals(SUCCESS)) linearToast.setBackgroundResource(R.color.colorPrimary)
        text.text = message
        val toast = Toast(activity)
        toast.duration = Toast.LENGTH_LONG
        toast.setGravity(Gravity.BOTTOM, 0, 103)
        toast.view = layout
        toast.show()
    }
    fun dialogList(title: String, onShow: (view: View, dialog: AlertDialog) -> Unit) {
        val dialog: AlertDialog
        val dialogBuilder = AlertDialog.Builder(activity!!)
        val view: View = layoutInflater.inflate(R.layout.dialog_default, null)
        view.tvJudulDialogDefault.text = "$title"
        dialogBuilder.setView(view)
        dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_shape_white)
        dialog.show()
        initRecycler(view.rvListDialog)
        view.btn_batal_dialog_default.setOnClickListener { dialog.dismiss() }
        onShow(view, dialog)
    }
}