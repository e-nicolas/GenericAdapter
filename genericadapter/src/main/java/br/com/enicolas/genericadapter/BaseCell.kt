package br.com.enicolas.genericadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * The base cell used to create the View Holders of GenericRecyclerAdapter
 */
open class BaseCell private constructor(view: View?, viewBinding: ViewBinding?): RecyclerView.ViewHolder(view ?: viewBinding!!.root) {

	constructor(view: View): this(view = view, viewBinding = null)
	constructor(viewBinding: ViewBinding): this(view = null, viewBinding = viewBinding)

	var view: View? = view ?: viewBinding?.root

	var onClick: ((Int) -> Unit)? = null

    init {
		view?.setOnClickListener {
            val index = adapterPosition
            if (index != RecyclerView.NO_POSITION) {
                onClick?.invoke(index)
            }
        }
    }

	/**
	 * Select the cell
	 */
    open fun setSelection(value:Boolean){}

	/**
	 * Method called when the cell is about to setup
	 */
    open fun prepareForReuse(){}
}
