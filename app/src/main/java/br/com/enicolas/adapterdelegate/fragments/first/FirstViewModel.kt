package br.com.enicolas.adapterdelegate.fragments.first

import androidx.lifecycle.ViewModel
import br.com.enicolas.genericadapter.adapter.GenericRecyclerAdapter

class FirstViewModel: ViewModel() {

    val adapter = GenericRecyclerAdapter()
    val list: List<Int> = (0..1000).toList()

}