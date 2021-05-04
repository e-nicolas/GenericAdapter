package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

/**
 * @param diffCallback - The custom diffCallback of type [DiffUtil.ItemCallback<T>]
 */
class Snapshot<T>(
    private val diffCallback: DiffUtil.ItemCallback<T>
) : SnapshotCore() {

    @Suppress("UNCHECKED_CAST")
    override var snapshotList: List<Any> = listOf()
        get() {
            return differ?.currentList ?: field
        }
        set(value) {
            adapter?.let { adapter ->
                if (differ == null)
                    this.differ = AsyncListDiffer(adapter, diffCallback)
                (differ as? AsyncListDiffer<T>)?.apply {
                    submitList(value as List<T>)
                    field = currentList as List<Any>
                }
            } ?: run {
                field = value
            }
        }
}
