package br.com.enicolas.genericadapter.single

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import br.com.enicolas.genericadapter.BaseCell
import br.com.enicolas.genericadapter.GenericRecyclerAdapter
import medcel.produto.expandido.android.core.recyclerview.adapter.single.IStringCell

/**
 * Just an Adapter to create a string list to recyclerView
 * OBS: You need to implement the IStringCell on your BaseCell
 */
class GenericStringRecyclerAdapter(
    private val cellType: Class<out BaseCell>,
    private val viewBindingClazz: Class<out ViewBinding>,
    private val list: List<String>,
    private val onClickAction: (String) -> Unit
): GenericRecyclerAdapter() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCell {
		val binding = getViewBinding(parent, viewBindingClazz)
		return cellType.getConstructor(viewBindingClazz)
			.newInstance(binding)
	}

	override fun onBindViewHolder(cell: BaseCell, position: Int) {
		super.onBindViewHolder(cell, position)

		(cell as? IStringCell)?.setText(list[position])

		cell.onClick = {
			delegate?.didSelectItemAtIndex(adapter = this, index = it)
			onClickAction(list[it])
		}
	}

	override fun getItemCount(): Int {
		return list.size
	}

}
