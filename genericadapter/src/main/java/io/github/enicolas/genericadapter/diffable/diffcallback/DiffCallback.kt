package io.github.enicolas.genericadapter.diffable.diffcallback

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DiffCallback() : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}
