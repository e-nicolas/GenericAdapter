package br.com.enicolas.genericadapter.sections

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.GenericRecyclerAdapter
import br.com.enicolas.genericadapter.GenericRecylerAdapterDelegate
import br.com.enicolas.genericadapter.IndexPath

open class GenericRecyclerSections {

	interface Delegate {
		fun numberOfSections(): Int { return 1 }
		fun numberOfRowsInSection(section: Int): Int { return 0 }
		fun cellForRowAt(indexPath: IndexPath, cell: RecyclerView.ViewHolder)
		fun didSelectRowAt(indexPath: IndexPath)
		fun registerCellAt(indexPath: IndexPath): AdapterHolderType?
		fun registerHeaderForSection(section: Int): AdapterHolderType?
		fun viewForHeaderInSection(section: Int, header: RecyclerView.ViewHolder)
	}

	var delegate: Delegate? = null

	val adapter: ConcatAdapter
	get() {
		return ConcatAdapter(getAdapters())
	}

	private fun getAdapters(): List<GenericRecyclerAdapter> {
		val adapters = arrayListOf<GenericRecyclerAdapter>()
		val numberOfSections = delegate?.numberOfSections() ?: 1
		for(section in 0 until numberOfSections) {
			val adapter = GenericRecyclerAdapter()
			adapters.add(adapter)
			adapter.delegate = object: GenericRecylerAdapterDelegate {
				override fun cellForPosition(
					adapter: GenericRecyclerAdapter,
					cell: RecyclerView.ViewHolder,
					position: Int
				) {
					delegate?.cellForRowAt(
						indexPath = IndexPath(section = section, row = position),
						cell = cell
					)
				}

				override fun registerCellAtPosition(
					adapter: GenericRecyclerAdapter,
					position: Int
				): AdapterHolderType? {
					return delegate?.registerCellAt(
						indexPath = IndexPath(section = section, row = position)
					)
				}

				override fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {
					delegate?.didSelectRowAt(
						indexPath = IndexPath(section = section, row = index)
					)
				}

				override fun numberOfRows(adapter: GenericRecyclerAdapter): Int {
					return delegate?.numberOfRowsInSection(section = section) ?: 0
				}

				override fun registerHeaderFor(adapter: GenericRecyclerAdapter): AdapterHolderType? {
					return delegate?.registerHeaderForSection(section)
				}

				override fun viewForHeaderAt(
					position: Int,
					cell: RecyclerView.ViewHolder,
					adapter: GenericRecyclerAdapter
				) {
					delegate?.viewForHeaderInSection(section, cell)
				}
			}
		}
		return adapters
	}

}
