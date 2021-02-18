package com.mandiri.news.view.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.mandiri.news.R
import com.mandiri.news.base.BaseActivity
import com.mandiri.news.model.ArticlesItem
import com.mandiri.news.utils.LoadScroll
import com.mandiri.news.view.adapter.NewsAdapter
import com.mandiri.news.view.view_model.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_kosong.*
import kotlinx.android.synthetic.main.toolbar_news.*
import kotlinx.android.synthetic.main.toolbar_transparant.btnBack
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsActivity : BaseActivity() {
    private val vm: NewsViewModel by viewModel()
    private var mAdapter: NewsAdapter? = null
    private var data: MutableList<ArticlesItem>? = null
    var key = ""
    var loadPlus: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBack.setOnClickListener { finish() }
        key = intent.getStringExtra("kategori")
        loadPlus = false
        initAPi()
        vmHandle()
        mAdapter = NewsAdapter()
        initRecycler(recyclerNews)
        recyclerNews.adapter = mAdapter

        val click = mAdapter?.clickEvent?.subscribe {
         val i = Intent(this, DetailActivity::class.java)
            i.putExtra("url", it.url)
            i.putExtra("desc", it.description)
         startActivity(i)
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter?.filter?.filter(newText)
                return false
            }

        })
        recyclerNews.addOnScrollListener(scrollData()!!)
        swipe.setOnRefreshListener {
            loadPlus = false
            recyclerNews.addOnScrollListener(scrollData()!!)
          initAPi()
        }
    }



    fun initAPi(){
        val param = HashMap<String, String>()
        param.apply {
            put("q", key)
            put("apiKey", "ca5c5c4d2622460398e81a81e4b1595b")
        }

        vm.getListNews(param)
    }

    private fun vmHandle() {
        vm.data.observe(this, Observer {
            if (it != null) {
                if (it.isNotEmpty()) {
                    linearKosong.visibility = View.GONE
                    recyclerNews.visibility = View.VISIBLE
                    if (loadPlus == false) {
                        swipe.isRefreshing = false
                        mAdapter?.setData(it)
                        mAdapter?.notifyDataSetChanged()

                    }
                    data = it
                } else {
                    recyclerNews.visibility = View.GONE
                    textMessage?.text = "Data tidak tersedia"
                    linearKosong.visibility = View.VISIBLE
                }
            }
        })
        vm.event.observe(this, Observer {
            if (it != null) {
                if (it.isLoading) {
                    linearKosong.visibility = View.GONE
                    if (!swipe.isRefreshing) if (data == null) swipe.isRefreshing = true
                } else {
                    linearKosong.visibility = View.VISIBLE
                    if (swipe.isRefreshing) swipe.isRefreshing = false
                }
                if (it.isSuccess != null) {
                    if (it.isSuccess) {
                    }
                }
                if (it.message != null) textMessage?.text = "${it.message}"
            }
        })
    }

    private fun scrollData(): LoadScroll? {
        return object : LoadScroll() {
            override fun onLoadMore() {
                loadPlusData()
            }
        }
    }

    private fun loadPlusData() {
        swipe.isRefreshing = true
        loadPlus = true
        initAPi()
    }
}