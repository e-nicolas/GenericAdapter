package br.com.enicolas.adapterdelegate.fragments.second

import androidx.lifecycle.ViewModel

class SecondViewModel: ViewModel() {
    var sections = mutableListOf(
        SectionModel("First Section", (0..10).toList()),
        SectionModel("Second Section", (0..20).toList()),
    )

    val originalSections = sections
}