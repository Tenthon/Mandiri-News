package com.mandiri.news.view.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mandiri.news.R
import kotlinx.android.synthetic.main.activity_home_news_category.*

class HomeNewsCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_news_category)
        cardOlahraga.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("kategori", "Sport")
            startActivity(i)
        }

        cardFashion.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("kategori", "Fashion")
            startActivity(i)
        }

        cardPolitik.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("kategori", "Politic")
            startActivity(i)
        }

        cardTeknologi.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("kategori", "technology")
            startActivity(i)
        }

        cardFinance.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("kategori", "finance")
            startActivity(i)
        }

        cardOpini.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("kategori", "opinion")
            startActivity(i)
        }
    }
}