package io.github.enicolas.genericadapter.diffable

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DiffCallback() : DiffUtil.ItemCallback<Any>() {

//    override fun getOldListSize(): Int = oldList.size
//
//    override fun getNewListSize(): Int = newList.size

//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
//    }
//
//    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
//        return oldList[oldPosition] == newList[newPosition]
//    }

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}