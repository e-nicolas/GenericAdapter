package br.com.enicolas.genericadapter

import androidx.viewbinding.ViewBinding
import br.com.enicolas.genericadapter.adapter.BaseCell

/**
 * Class used to store the cell reuseIdentifier,
 * BaseCell class
 * and its LayoutRes our ViewBinding
 */
class AdapterHolderType private constructor(
	var resId: Int?,
	var viewBindingClazz: Class<out ViewBinding>?,
	var clazz: Class<out BaseCell>,
	var reuseIdentifier: Int
) {
	constructor(resId: Int,
				clazz: Class<out BaseCell>,
				reuseIdentifier: Int
    ) : this(
		resId = resId,
		viewBindingClazz = null,
		clazz = clazz,
		reuseIdentifier = reuseIdentifier
	)

	constructor(viewBinding: Class<out ViewBinding>?,
				clazz: Class<out BaseCell>,
				reuseIdentifier: Int
    ) : this(
		resId = null,
		viewBindingClazz = viewBinding,
		clazz = clazz,
		reuseIdentifier = reuseIdentifier
	)
}
