package br.com.enicolas.genericadapter.adapter

import androidx.recyclerview.widget.DiffUtil
import br.com.enicolas.genericadapter.diffable.DiffCallback

open class GenericDiffableRecyclerAdapter<T> : GenericRecyclerAdapter() {
    var snapshot: List<T> = listOf()
        set(value) {
            val diffCallback = DiffCallback(snapshot, value)
            val result = DiffUtil.calculateDiff(diffCallback)
            field = value
            result.dispatchUpdatesTo(this)
        }
}
