package br.com.enicolas.adapterdelegate.fragments.second

import androidx.lifecycle.ViewModel
import br.com.enicolas.genericadapter.sections.GenericRecyclerSections

class SecondViewModel: ViewModel() {
    var sections = mutableListOf(
        SectionModel("First Section", (0..10).toList()),
        SectionModel("Second Section", (0..20).toList()),
    )

    val originalSections = sections
}