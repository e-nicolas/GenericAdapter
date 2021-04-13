package br.com.enicolas.genericadapter.sections

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.IndexPath
import br.com.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import br.com.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate
import java.lang.ref.WeakReference

open class GenericRecyclerSections {

    /**
     * Delegate
     */
    var delegate: SectionDelegate? = null
        set(value) {
            field = value
			reloadData()
        }

    /**
     * Concat Adapter
     */
    var adapter = ConcatAdapter()
        private set

    /**
     * RecyclerView weak reference
     */
	private var recyclerView: WeakReference<RecyclerView>? = null

    /**
     * Recreate the [ConcatAdapter] and set the recyclerView adapter to the new one
     */
	fun reloadData() {
		adapter = ConcatAdapter(getAdapters())
        recyclerView?.get()?.adapter = adapter
	}

    /**
     * Attach a recyclerView reference to this class
     */
	fun attachRecyclerView(recyclerView: RecyclerView) {
		this.recyclerView = WeakReference(recyclerView)
		reloadData()
	}

    /**
     * Create x number of [GenericRecyclerAdapter] based on
     * numberOfSections delegate
     */
    private fun getAdapters(): List<GenericRecyclerAdapter> {
		val adapters = arrayListOf<GenericRecyclerAdapter>()
        val numberOfSections = delegate?.numberOfSections() ?: 1
        for (section in 0 until numberOfSections) {
            val genericAdapter = GenericRecyclerAdapter()
            genericAdapter.tag = section
            genericAdapter.delegate = adapterDelegate
			adapters.add(genericAdapter)
        }
		return adapters
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
}
