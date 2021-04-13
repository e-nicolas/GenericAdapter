package br.com.enicolas.genericadapter.sections

import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.IndexPath

interface SectionDelegate {
    /**
     * Total of sections in adapter
     */
    fun numberOfSections(): Int { return 1 }

    /**
     * Number of rows in each section
     */
    fun numberOfRowsInSection(section: Int): Int

    /**
     * When the cell is ready to reuse
     */
    fun cellForRowAt(indexPath: IndexPath, cell: RecyclerView.ViewHolder)

    /**
     * When the user touch the cell
     */
    fun didSelectRowAt(indexPath: IndexPath) {}

    /**
     * Defines the Type and the Layout of your cell at this position
     */
    fun registerCellAt(indexPath: IndexPath): AdapterHolderType?

    /**
     * Defines the Type and the Layout of your header at this section
     */
    fun registerHeaderForSection(section: Int): AdapterHolderType? { return null }

    /**
     * When the header is ready to reuse
     */
    fun viewForHeaderInSection(section: Int, header: RecyclerView.ViewHolder) {}
}