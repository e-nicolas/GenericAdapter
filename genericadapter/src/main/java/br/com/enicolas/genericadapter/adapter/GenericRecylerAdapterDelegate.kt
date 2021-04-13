package br.com.enicolas.genericadapter.adapter

import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.genericadapter.AdapterHolderType

/**
 * Interface to handle the recyclerview state inside GenericRecyclerViewAdapter
 */
interface GenericRecylerAdapterDelegate {

    /**
     * Retrieves the cell at this position. This method is ready to render and setup your cell
     */
    fun cellForPosition(
		adapter: GenericRecyclerAdapter,
		cell: RecyclerView.ViewHolder,
		position: Int
	)

    /**
     * Defines the Type and the Layout of your cell at this position
     */
    fun registerCellAtPosition(adapter: GenericRecyclerAdapter, position: Int): AdapterHolderType? {
        return null
    }

    /**
     * When the user touch the cell
     */
    fun didSelectItemAtIndex(adapter: GenericRecyclerAdapter, index: Int) {}

    /**
     * Number of rows in recyclerView
     */
    fun numberOfRows(adapter: GenericRecyclerAdapter): Int

    /**
     * Defines the Type and the Layout of your header at this position
     */
    fun registerHeaderFor(adapter: GenericRecyclerAdapter): AdapterHolderType? {
        return null
    }

    /**
     * When the header is ready to reuse
     */
    fun viewForHeaderAt(
		position: Int,
		cell: RecyclerView.ViewHolder,
		adapter: GenericRecyclerAdapter
	) {
    }
}
