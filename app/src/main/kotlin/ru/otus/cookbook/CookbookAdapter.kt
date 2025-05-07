package ru.otus.cookbook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cookbook.data.RecipeListItem

class CookbookAdapter(private val listener: Listener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = listOf<RecipeListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewTypes.CATEGORY.id -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_recipe_category, parent, false)
                CategoryViewHolder(view)
            }
            ViewTypes.RECIPE.id -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_recipe_item, parent, false)
                RecipeViewHolder(view, listener)
            }
            else -> throw IllegalArgumentException("Not found view type for chat adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list.getOrNull(position) ?: return
        when (item) {
            is RecipeListItem.CategoryItem -> {
                (holder as? CategoryViewHolder)?.bind(item)
            }
            is RecipeListItem.RecipeItem -> {
                (holder as? RecipeViewHolder)?.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is RecipeListItem.CategoryItem -> ViewTypes.CATEGORY.id
            is RecipeListItem.RecipeItem -> ViewTypes.RECIPE.id
            else -> -1
        }
    }

    fun setItems(items: List<RecipeListItem>) {
        list = items
        notifyDataSetChanged()
    }

    enum class ViewTypes(val id: Int) {
        CATEGORY(R.layout.vh_recipe_category),
        RECIPE(R.layout.vh_recipe_item)
    }
}