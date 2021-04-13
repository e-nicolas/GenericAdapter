package br.com.enicolas.adapterdelegate.fragments.first

import androidx.lifecycle.ViewModel
import br.com.enicolas.genericadapter.adapter.GenericDiffableRecyclerAdapter
import br.com.enicolas.genericadapter.adapter.GenericRecyclerAdapter

class FirstViewModel: ViewModel() {

    val adapter = GenericDiffableRecyclerAdapter<Int>()
    var list: MutableList<Int> = (0..1000).toMutableList()
    val originalList = list
}