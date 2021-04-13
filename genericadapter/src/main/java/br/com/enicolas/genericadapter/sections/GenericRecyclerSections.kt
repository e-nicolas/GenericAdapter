package br.com.enicolas.genericadapter.sections

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.IndexPath
import br.com.enicolas.genericadapter.adapter.GenericRecyclerAdapter
import br.com.enicolas.genericadapter.adapter.GenericRecylerAdapterDelegate
import java.lang.ref.WeakReference

open class GenericRecyclerSections {

    var delegate: SectionDelegate? = null
        set(value) {
            field = value
			reloadData()
        }

    var adapter = ConcatAdapter()
        private set

	private var recyclerView: WeakReference<RecyclerView>? = null

	fun reloadData() {
		adapter = ConcatAdapter(getAdapters())
        recyclerView?.get()?.adapter = adapter
	}

	fun attachRecyclerView(recyclerView: RecyclerView) {
		this.recyclerView = WeakReference(recyclerView)
		reloadData()
	}

    /**
     * Setup adapters
     */
    private fun getAdapters(): List<GenericRecyclerAdapter> {
		val adapters = arrayListOf<GenericRecyclerAdapter>()
        val numberOfSections = delegate?.numberOfSections() ?: 1
        for (section in 0 until numberOfSections) {
            val genericAdapter = GenericRecyclerAdapter()
            genericAdapter.tag = section
            genericAdapter.delegate = adapterDelegate
			adapters.add(genericAdapter)
//            adapter.addAdapter(genericAdapter)
        }
		return adapters
    }

    /**
     * Adapter delegate
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
