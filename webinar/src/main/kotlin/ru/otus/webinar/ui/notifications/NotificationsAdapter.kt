package ru.otus.webinar.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.otus.webinar.databinding.VhNotificationBinding
import java.util.Locale

/**
 * Notifications adapter
 */
class NotificationsAdapter(private val onClick: (Int) -> Unit) : ListAdapter<Pair<Int, String>, NotificationsAdapter.NotificationHolder>(NotificationDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        return NotificationHolder(
            VhNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationHolder(private val binding: VhNotificationBinding, private val onClick: (Int) -> Unit) : ViewHolder(binding.root) {
        private var id: Int = -1

        init {
            binding.root.setOnClickListener { onClick(id) }
        }

        fun bind(notification: Pair<Int, String>) = with(binding) {
            this@NotificationHolder.id = notification.first
            id.text = String.format(Locale.getDefault(), "%02d", notification.first)
            title.text = notification.second
        }
    }
}

private object NotificationDiff : DiffUtil.ItemCallback<Pair<Int, String>>() {
    override fun areItemsTheSame(oldItem: Pair<Int, String>, newItem: Pair<Int, String>): Boolean = oldItem.first == newItem.first
    override fun areContentsTheSame(oldItem: Pair<Int, String>, newItem: Pair<Int, String>): Boolean = oldItem.second == newItem.second
}