package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.AsyncListDiffer
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter

/**
 *  The abstract class to store adapter data.
 *  adapter - The recyclerview adapter attached to the [AsyncListDiffer]
 *  snapshotList - The current list we pass to the adapter
 *  differ - The [AsyncListDiffer] that process the diff
 */
abstract class SnapshotCore {
    var adapter: GenericRecyclerAdapter? = null
    abstract var snapshotList: List<Any>
    internal var differ: AsyncListDiffer<*>? = null
}
