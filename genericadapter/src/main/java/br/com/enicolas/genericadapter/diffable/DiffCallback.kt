package br.com.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.DiffUtil

class DiffCallback<T>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }
}