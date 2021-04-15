package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

class CustomSnapshot<T>(
    private val customDiffCallback: DiffUtil.ItemCallback<T>
) : SnapshotCore() {

    override fun updateSnapshot(value: List<Any>) {
        adapter?.let { adapter ->
            if (differ == null)
                this.differ = AsyncListDiffer(adapter, customDiffCallback)
            (differ as AsyncListDiffer<T>).apply {
                submitList(value as List<T>)
                snapshot = currentList as List<Any>
            }
        } ?: run {
            snapshot = value
        }
    }
}
