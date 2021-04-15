package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.AsyncListDiffer

class Snapshot : SnapshotCore() {

    @Suppress("UNCHECKED_CAST")
    override fun updateSnapshot(value: List<Any>) {
        adapter?.let { adapter ->
            if (differ == null)
                this.differ = AsyncListDiffer(adapter, DiffCallback())
            (differ as AsyncListDiffer<Any>).submitList(value)
            snapshot = differ?.currentList ?: value
        } ?: run {
            snapshot = value
        }
    }
}
