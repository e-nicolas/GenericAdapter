package io.github.enicolas.genericadapter.diffable

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.diffable.diffcallback.SectionDiffCallback

/**
 * @param concatAdapter - The main adapter to add and remove sub adapters
 */
class SectionSnapshot(
    private var concatAdapter: ConcatAdapter
) : SnapshotCore() {

    private val diff = SectionDiffCallback()

    @Suppress("UNCHECKED_CAST")
    override var snapshotList: List<Any> = listOf()
        set(value) {
            val oldList = snapshotList.toList() as List<GenericRecyclerAdapter>
            val newList = value as List<GenericRecyclerAdapter>
            field = newList
            diff.setList(newList, oldList)
            val result = DiffUtil.calculateDiff(diff, true)
            result.dispatchUpdatesTo(listUpdateCallback)
            diff.updateIndexes()
        }

    /**
     * List update listener
     */
    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
//            println("Inserted: ${position} and count: ${count}")
            for (i in 0 until count) {
                (snapshotList[position + i] as? GenericRecyclerAdapter)?.let {
                    concatAdapter.addAdapter(it)
                }
            }
        }

        override fun onRemoved(position: Int, count: Int) {
//            println("Removed: ${position} and count: ${count}")
            val adaptersToBeRemoved =
                arrayListOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>?>()
            for (i in 0 until count) {
                adaptersToBeRemoved.add(concatAdapter.adapters.getOrNull(position + i))
            }
            adaptersToBeRemoved.forEach { adapter ->
                adapter?.let { concatAdapter.removeAdapter(it) }
            }
            concatAdapter.notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
//            println("Moved: ${fromPosition} to ${toPosition}")
            TODO("Not yet implemented")
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
//            println("onChanged: ${position} and count: ${count}")
            for (i in 0 until count) {
                concatAdapter.adapters.getOrNull(position + i)?.notifyDataSetChanged()
            }
        }
    }
}