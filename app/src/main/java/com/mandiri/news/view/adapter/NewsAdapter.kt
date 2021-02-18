package com.mandiri.news.view.adapter

import android.graphics.Bitmap
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.mandiri.news.R
import com.mandiri.news.model.ArticlesItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_all.view.*


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.IViewHolder>(), Filterable {

    private val onClick = PublishSubject.create<ArticlesItem>()

    private var models: MutableList<ArticlesItem> = ArrayList()
    var modelList: MutableList<ArticlesItem>

    init {
        modelList = models
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_all, parent, false)
    )

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                modelList = p1!!.values as MutableList<ArticlesItem>
                notifyDataSetChanged()
            }

            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString: String = p0.toString()
                modelList = if (charString.isEmpty()) {
                    models
                } else {
                    val filteredList: MutableList<ArticlesItem> = mutableListOf()
                    for (s: ArticlesItem in models) {
                        when {
                            s.title!!.toLowerCase().contains(charString.toLowerCase()) -> filteredList.add(
                                s
                            )
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = modelList
                return filterResults
            }
        }
    }

    override fun getItemCount(): Int = modelList.size
    override fun getItemViewType(position: Int): Int = super.getItemViewType(position)

    override fun onBindViewHolder(holder: IViewHolder, position: Int) {
        if (modelList.size > 0) {
            val item = modelList[position]
            holder.bindModel(item)
        }
    }

    fun setDataClear(data: MutableList<ArticlesItem>) {
        if (models.size >= 0) {
            models.clear()
        }
        models.addAll(data)
        notifyDataSetChanged()
    }

    fun setData(data: MutableList<ArticlesItem>) {
//        if (models.size >= 0) {
//            models.clear()
//        }
        models.addAll(data)
        //notifyDataSetChanged()
    }


    val clickEvent: Observable<ArticlesItem> = onClick


    inner class IViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.onNext(modelList[layoutPosition])
            }

        }

        fun bindModel(model: ArticlesItem) {
            itemView.tvJudul.text = model.title
            val fullDescSpannedShort =
                Html.fromHtml(model.description)
            val shortDec = fullDescSpannedShort.toString()
            itemView.tvIsi.text = shortDec
            itemView.tvReporter.text = model.author
            itemView.tvTime.text = model.publishedAt
            Glide.with(itemView.context).load(model.urlToImage).asBitmap().placeholder(R.drawable.ic_not_image)
                .into(object : BitmapImageViewTarget(itemView.imgNews) {
                    override fun setResource(resource: Bitmap?) {
                        super.setResource(resource)
                        val circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(
                                itemView.context.resources,
                                resource
                            )
                        circularBitmapDrawable.cornerRadius = 5f
                        itemView.imgNews.setImageDrawable(circularBitmapDrawable)
                    }
                })
        }
    }


}