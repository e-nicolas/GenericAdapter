package br.com.enicolas.genericadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.diffable.Snapshot

open class GenericRecyclerAdapter(snapshot: Snapshot? = null) : RecyclerView.Adapter<BaseCell>() {

    /**
     * Variables
     */
    var snapshot: Snapshot? = null
    set(value) {
        field = value
        snapshot?.adapter = this
    }

    private var _selectedItem = RecyclerView.NO_POSITION
    val selectedItem: Int
        get() {
            return _selectedItem
        }

    protected var cellTypes = arrayListOf<AdapterHolderType>()
    var tag: Int = 0

    /**
     * Delegate
     */
    var delegate: GenericRecylerAdapterDelegate? = null

    /**
     * Init
     */
    init {
        this.snapshot = snapshot
    }

    /**
     * When the view of cell will inflate
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseCell {

        cellTypes.find { it.reuseIdentifier == viewType }?.let { adapterHolderType ->
            adapterHolderType.viewBindingClazz?.let { viewBindingClazz ->
                val binding = getViewBinding(parent, viewBindingClazz)
                return adapterHolderType.clazz.getDeclaredConstructor(viewBindingClazz)
                    .newInstance(binding)
            }

            adapterHolderType.resId?.let { resId ->
                return adapterHolderType.clazz.getDeclaredConstructor(View::class.java)
                    .newInstance(LayoutInflater.from(parent.context).inflate(resId, parent, false))
            }

        }

        throw Error("You need to implement GenericRecyclerAdapterDelegate!")
    }

    /**
     * Render and setup the view holder
     */
    override fun onBindViewHolder(cell: BaseCell, position: Int) {
        if (isHeaderPosition(position)) {
            delegate?.viewForHeaderAt(position, cell, this)
        } else {
            val newPosition = getNormalizedPosition(position)
            cell.prepareForReuse()
            cell.setSelection(selectedItem == newPosition)
            delegate?.cellForPosition(adapter = this, cell = cell, position = newPosition)

            cell.onClick = {
                delegate?.didSelectItemAtIndex(adapter = this, index = newPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return (delegate?.numberOfRows(this) ?: 0) + getIndexOffset()
    }

    /**
     * Adds to de cellTypes array the class Type of the BaseCell and returns its reuseIdentifier
     * to inflate it on [onCreateViewHolder]
     */
    override fun getItemViewType(position: Int): Int {
        delegate?.let { delegate ->
            if (isHeaderPosition(position)) {
                delegate.registerHeaderFor(this)?.let { holderType ->
                    cellTypes.add(holderType)
                    return holderType.reuseIdentifier
                }
            } else {
                val newPosition = getNormalizedPosition(position)
                delegate.registerCellAtPosition(this, newPosition)?.let { type ->
                    cellTypes.add(type)
                    return type.reuseIdentifier
                }
            }
        }
        return super.getItemViewType(position)
    }

    /**
     * Gets the inflater
     */
    protected fun getViewBinding(
		parent: ViewGroup,
		viewBindingClazz: Class<out ViewBinding>
	): ViewBinding {
        val inflater = LayoutInflater.from(parent.context)
        return viewBindingClazz.getMethod(
			"inflate",
			LayoutInflater::class.java,
			ViewGroup::class.java,
			Boolean::class.java
		).invoke(null, inflater, parent, false) as ViewBinding
    }

    /**
     * THe header position sum
     */
    protected fun getIndexOffset(): Int {
        return if (hasHeader()) 1 else 0
    }

    /**
     * Get the position with the header offset
     */
    private fun getNormalizedPosition(position: Int): Int {
        return position - getIndexOffset()
    }

    /**
     * Check if user wants to register a cell
     */
    private fun hasHeader(): Boolean {
        return delegate?.registerHeaderFor(this) != null
    }

    /**
     * If adapter is looping a header position
     */
    private fun isHeaderPosition(position: Int): Boolean {
        return hasHeader() && position == 0
    }

    fun selectItemAt(index: Int) {
        notifyItemChanged(selectedItem)
        _selectedItem = index
        notifyItemChanged(selectedItem)
    }
}
