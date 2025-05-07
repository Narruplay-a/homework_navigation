package ru.otus.cookbook

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cookbook.data.RecipeListItem

class CategoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView by lazy { view.findViewById(R.id.textView) }

    fun bind(item: RecipeListItem.CategoryItem) {
        name.text = item.name
    }
}