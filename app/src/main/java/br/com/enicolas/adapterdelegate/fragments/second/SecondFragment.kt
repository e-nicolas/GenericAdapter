package br.com.enicolas.adapterdelegate.fragments.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.enicolas.adapterdelegate.R
import br.com.enicolas.adapterdelegate.cells.FirstCell
import br.com.enicolas.adapterdelegate.cells.HeaderCell
import br.com.enicolas.adapterdelegate.cells.SecondCell
import br.com.enicolas.adapterdelegate.databinding.CellFirstBinding
import br.com.enicolas.adapterdelegate.databinding.CellHeaderBinding
import br.com.enicolas.adapterdelegate.databinding.CellSecondBinding
import br.com.enicolas.adapterdelegate.databinding.FragmentSecondBinding
import br.com.enicolas.genericadapter.AdapterHolderType
import br.com.enicolas.genericadapter.IndexPath
import br.com.enicolas.genericadapter.helpers.SwipeTouchCallback
import br.com.enicolas.genericadapter.sections.GenericRecyclerSections
import br.com.enicolas.genericadapter.sections.SectionDelegate

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding
        get() {
            return _binding!!
        }

    private val viewModel: SecondViewModel by viewModels()
    private val genericSections = GenericRecyclerSections()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
    }

    /**
     * Setup recycler view
     */
    private fun setupRecyclerView() {
        genericSections.delegate = recyclerDelegate
        binding.recyclerView.adapter = genericSections.adapter
        genericSections.reloadData()
        val touchCallback = SwipeTouchCallback()
        touchCallback.didSwipeItemAt = {
            println("Swipe em: ${genericSections.getRelativePosition(it)}")
        }
        val itemTouchHelper = ItemTouchHelper(touchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    /**
     * Setup SearchView
     */
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(searchViewListener)
    }

    /**
     * RecyclerView Delegate
     */
    private val recyclerDelegate = object : SectionDelegate {

        override fun numberOfSections(): Int {
            return viewModel.sections.size
        }

        override fun numberOfRowsInSection(section: Int): Int {
            return viewModel.sections[section].list.size
        }

        override fun cellForRowAt(indexPath: IndexPath, cell: RecyclerView.ViewHolder) {
            val sectionModel = viewModel.sections[indexPath.section]
            val list = sectionModel.list[indexPath.row]
            val text = getString(R.string.itemString, list.toString())
            when (indexPath.section) {
                0 -> {
                    (cell as? FirstCell)?.binding?.textView?.text = text
                }
                1 -> {
                    (cell as? SecondCell)?.binding?.textView?.text = text
                }
            }
        }

        override fun registerCellAt(indexPath: IndexPath): AdapterHolderType {
            return when (indexPath.section) {
                0 -> {
                    AdapterHolderType(
                        viewBinding = CellFirstBinding::class.java,
                        clazz = FirstCell::class.java,
                        reuseIdentifier = 0
                    )
                }
                else -> {
                    AdapterHolderType(
                        viewBinding = CellSecondBinding::class.java,
                        clazz = SecondCell::class.java,
                        reuseIdentifier = 1
                    )
                }
            }
        }

        override fun registerHeaderForSection(section: Int): AdapterHolderType {
            return AdapterHolderType(
                viewBinding = CellHeaderBinding::class.java,
                clazz = HeaderCell::class.java,
                reuseIdentifier = 2
            )
        }

        override fun viewForHeaderInSection(section: Int, header: RecyclerView.ViewHolder) {
            (header as? HeaderCell)?.binding?.textView?.text = viewModel.sections[section].title
        }

        override fun didSelectRowAt(indexPath: IndexPath) {
            Toast.makeText(
                context,
                "Section: ${indexPath.section}, Row: ${indexPath.row}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * SearchView Listener
     */
    private val searchViewListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            viewModel.sections = viewModel.originalSections.map { section ->
                val originalList = section.list
                val filteredList =
                    if(newText.isNullOrBlank())
                        section.list
                    else
                        originalList.filter { it.toString() == newText }

                SectionModel(
                    title = section.title,
                    list = filteredList
                )
            }.filter { it.list.isNotEmpty() }.toMutableList()
            genericSections.reloadData()
            return true
        }
    }
}