package br.com.enicolas.genericadapter.sections

import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.IndexPath

interface SectionDelegate {
    fun numberOfSections(): Int { return 1 }
    fun numberOfRowsInSection(section: Int): Int { return 0 }
    fun cellForRowAt(indexPath: IndexPath, cell: RecyclerView.ViewHolder)
    fun didSelectRowAt(indexPath: IndexPath)
    fun registerCellAt(indexPath: IndexPath): AdapterHolderType?
    fun registerHeaderForSection(section: Int): AdapterHolderType?
    fun viewForHeaderInSection(section: Int, header: RecyclerView.ViewHolder)
}