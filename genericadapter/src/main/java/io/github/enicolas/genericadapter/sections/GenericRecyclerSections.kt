package io.github.enicolas.genericadapter.sections

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.enicolas.genericadapter.AdapterHolderType
import io.github.enicolas.genericadapter.IndexPath
import io.github.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import io.github.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate
import io.github.enicolas.genericadapter.diffable.SectionSnapshot
import io.github.enicolas.genericadapter.diffable.SnapshotDefault

open class GenericRecyclerSections {

    /**
     * Delegate
     */
    var delegate: SectionDelegate? = null

    /**
     * Concat Adapter
     */
    var adapter = ConcatAdapter()
        private set

    /**
     * Snapshot
     */
    private var snapshot: SectionSnapshot = SectionSnapshot(adapter)

    /**
     * Recreate the [ConcatAdapter] and set the recyclerView adapter to the new one
     */
    fun reloadData() {
        snapshot.snapshotList = createAdapters()
    }

    /**
     * Create x number of [GenericRecyclerAdapter] based on
     * numberOfSections delegate
     */
    private fun createAdapters() : List<GenericRecyclerAdapter> {
        val adapters = arrayListOf<GenericRecyclerAdapter>()
        val numberOfSections = delegate?.numberOfSections() ?: 1
        for (section in 0 until numberOfSections) {
            adapters.add(createAdapterFor(section = section))
        }
        return adapters
    }

    /**
     * Create a [GenericRecyclerAdapter] based on section
     */
    private fun createAdapterFor(section: Int): GenericRecyclerAdapter {
        val genericAdapter = GenericRecyclerAdapter(SnapshotDefault())
        genericAdapter.tag = section
        genericAdapter.delegate = adapterDelegate
        return genericAdapter
    }

    /**
     * Adapter delegate [GenericRecylerAdapterDelegate]
     */
    private val adapterDelegate = object : GenericRecylerAdapterDelegate {
        override fun cellForPosition(
            adapter: GenericRecyclerAdapter,
            cell: RecyclerView.ViewHolder,
            position: Int
        ) {
            delegate?.cellForRowAt(
                indexPath = IndexPath(section = adapter.tag, row = position),
                cell = cell
            )
        }

        override fun registerCellAtPosition(
            adapter: GenericRecyclerAdapter,
            position: Int
        ): AdapterHolderType? {
            return delegate?.registerCellAt(
                indexPath = IndexPath(section = adapter.tag, row = position)
            )
        }

        override fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {
            delegate?.didSelectRowAt(
                indexPath = IndexPath(section = adapter.tag, row = index)
            )
        }

        override fun numberOfRows(adapter: GenericRecyclerAdapter): Int {
            return delegate?.numberOfRowsInSection(section = adapter.tag) ?: 0
        }

        override fun registerHeaderFor(adapter: GenericRecyclerAdapter): AdapterHolderType? {
            return delegate?.registerHeaderForSection(section = adapter.tag)
        }

        override fun viewForHeaderAt(
            position: Int,
            cell: RecyclerView.ViewHolder,
            adapter: GenericRecyclerAdapter
        ) {
            delegate?.viewForHeaderInSection(section = adapter.tag, header = cell)
        }
    }

    /**
     * Returns a [GenericRecyclerAdapter] based on section position
     */
    fun adapterForPosition(position: Int): GenericRecyclerAdapter {
        return adapter.adapters[position] as GenericRecyclerAdapter
    }

    /**
     * Based on absolute position returns the section of the item position
     */
    fun getSectionFor(position: Int): Int {
        val numberOfSections = delegate?.numberOfSections() ?: 1
        var rowCount = 0
        for (section in 0 until numberOfSections) {
            val adapter = adapterForPosition(section)
            rowCount += adapter.itemCount
            if (position < rowCount) {
                return section
            }
        }
        return 0
    }

    /**
     * Transform the absolute position to the position of the item inside the adapter
     */
    fun getRelativePosition(position: Int): Int {
        val numberOfSections = delegate?.numberOfSections() ?: 1
        var cumulativeRowCount = 0
        for (section in 0 until numberOfSections) {
            val adapter = adapterForPosition(section)
            val currentItemsCount = adapter.itemCount
            cumulativeRowCount += currentItemsCount
            if (position < cumulativeRowCount) {
                val relativePosition = position - (cumulativeRowCount - currentItemsCount)
                return adapter.getNormalizedPosition(relativePosition)
            }
        }
        return 0
    }
}
