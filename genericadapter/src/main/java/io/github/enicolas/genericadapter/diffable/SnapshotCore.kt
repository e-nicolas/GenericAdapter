package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.AsyncListDiffer
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter

abstract class SnapshotCore {
    var adapter: GenericRecyclerAdapter? = null
    internal var snapshot: List<Any> = listOf()

    internal var differ: AsyncListDiffer<*>? = null

    fun getCurrentList(): Int =
        differ?.currentList?.size
            ?: snapshot.size

    abstract fun updateSnapshot(value: List<Any>)
}
