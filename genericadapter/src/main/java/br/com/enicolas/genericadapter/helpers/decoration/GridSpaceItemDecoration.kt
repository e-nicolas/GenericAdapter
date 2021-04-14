package br.com.enicolas.genericadapter.helpers.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration() : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layout = parent.layoutManager as GridLayoutManager
        val space = layout.paddingStart
        outRect.top = space
        outRect.bottom = space
        outRect.left = space
        outRect.right = space
    }
}
