package io.github.enicolas.genericadapter.helpers.decoration

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecoration(var offset:Int = 15) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val offsetDp: Int = (offset * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

        val firstPosition = parent.getChildAdapterPosition(view) == 0
        val lastPosition = parent.getChildAdapterPosition(view) == state.itemCount -1

        if(state.itemCount == 2){
            outRect.left = offsetDp
            outRect.right = offsetDp
            if(firstPosition) {
                outRect.right = 0
            }
            return
        }


        if(firstPosition){
            outRect.left = offsetDp
            outRect.right = offsetDp/2
            return
        }
        if(lastPosition){
            outRect.left = offsetDp/2
            outRect.right = offsetDp
            return
        }

        outRect.left = offsetDp/2
        outRect.right = offsetDp/2
    }
}