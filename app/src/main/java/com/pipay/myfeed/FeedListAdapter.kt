package com.pipay.myfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import jp.wasabeef.glide.transformations.BlurTransformation

class FeedListAdapter: ListAdapter<Post, FeedListAdapter.FeedViewHolder>(itemCallback) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_post
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val itemCallback = object: DiffUtil.ItemCallback<Post>() {

            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class FeedViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView: AppCompatImageView = itemView.findViewById(R.id.imageView)
        private val textViewTitle: MaterialTextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewSubtitle: MaterialTextView = itemView.findViewById(R.id.textViewSubtitle)

        fun bind(post: Post) {

            val request1 = Glide.with(itemView)
                .load(post.thumbnailUrl)
                .apply(RequestOptions.bitmapTransform(
                    BlurTransformation(25, 3)
                ).centerCrop())



            val request2 = Glide.with(itemView)
                .load(post.thumbnailUrl2)
                .apply(RequestOptions.bitmapTransform(
                    BlurTransformation(10, 3)
                ).centerCrop())
                .thumbnail(request1)

            Glide.with(itemView)
                .load(post.imageUrl)
                .thumbnail(request2)
                .centerCrop()
                .into(imageView)
            textViewTitle.text = post.title?.capitalize()
            textViewSubtitle.text = post.body
        }
    }


}