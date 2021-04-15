package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.AsyncListDiffer

class SnapshotDefault : SnapshotCore() {

    @Suppress("UNCHECKED_CAST")
    override var snapshotList: List<Any> = listOf()
        get() {
            return differ?.currentList ?: field
        }
        set(value) {
            adapter?.let { adapter ->
                if (differ == null)
                    this.differ = AsyncListDiffer(adapter, DiffCallback())
                (differ as? AsyncListDiffer<Any>)?.submitList(value)
                field = differ?.currentList ?: value
            } ?: run {
                field = value
            }
        }
}
