package ru.otus.cookbook

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cookbook.data.RecipeListItem

class RecipeViewHolder(
    private val view: View,
    private val listener: Listener): RecyclerView.ViewHolder(view) {

        private val twName: TextView by lazy { view.findViewById(R.id.tw_name) }
    private val twDescription: TextView by lazy { view.findViewById(R.id.tw_description) }

    fun bind(item: RecipeListItem.RecipeItem) {
        twName.text = item.title
        twDescription.text = item.description

        view.setOnClickListener {
            listener.onItemClicked(item.id)
        }
    }
}