package io.github.enicolas.adapterdelegate.fragments.first

import androidx.lifecycle.ViewModel

class FirstViewModel: ViewModel() {
    var list: MutableList<Item> = (0..1000).map { Item(id = it) }.toMutableList()
    val originalList = list
}