package br.com.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.DiffUtil
import br.com.enicolas.genericadapter.adapter.GenericRecyclerAdapter

class Snapshot {
    var adapter: GenericRecyclerAdapter? = null
    private var snapshot: List<Any> = listOf()

    @Suppress("UNCHECKED_CAST")
    fun updateSnapshot(value: List<Any>) {
        adapter?.let { adapter ->
            val diffCallback = DiffCallback(snapshot, value)
            val result = DiffUtil.calculateDiff(diffCallback)
            snapshot = value
            result.dispatchUpdatesTo(adapter)
        } ?: run {
            snapshot = value
        }
    }
}