package io.github.enicolas.genericadapter.diffable.diffcallback

import androidx.recyclerview.widget.DiffUtil
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter

class SectionDiffCallback : DiffUtil.Callback() {

    private var oldListItemCount: List<Int> = listOf()
    private var newList: List<GenericRecyclerAdapter> = listOf()
    private var oldList: List<GenericRecyclerAdapter> = listOf()

    fun setList(newList: List<GenericRecyclerAdapter>, oldList: List<GenericRecyclerAdapter>) {
        this.newList = newList
        this.oldList = oldList
    }

    fun updateIndexes() {
        oldListItemCount = newList.map { it.itemCount }
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.getOrNull(oldItemPosition)?.tag == newList.getOrNull(oldItemPosition)?.tag
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAdapter = oldList[oldItemPosition]
        val newAdapter = newList[newItemPosition]
        if (oldAdapter.tag == newAdapter.tag) {
            println("OLD: ${oldListItemCount[oldItemPosition]}")
            println("NEW: ${newAdapter.itemCount}")
            return oldListItemCount[oldItemPosition] == newAdapter.itemCount
        }
        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return true
    }
}