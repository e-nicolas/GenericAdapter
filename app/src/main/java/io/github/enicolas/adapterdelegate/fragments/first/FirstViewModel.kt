package io.github.enicolas.adapterdelegate.fragments.first

import androidx.lifecycle.ViewModel

class FirstViewModel: ViewModel() {
    var list: MutableList<Int> = (0..1000).toMutableList()
    val originalList = list
}