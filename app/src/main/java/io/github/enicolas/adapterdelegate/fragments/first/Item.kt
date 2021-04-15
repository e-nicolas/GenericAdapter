package io.github.enicolas.adapterdelegate.fragments.first

data class Item(
    val id: Int
) {
    override fun toString(): String {
        return this.id.toString()
    }
}