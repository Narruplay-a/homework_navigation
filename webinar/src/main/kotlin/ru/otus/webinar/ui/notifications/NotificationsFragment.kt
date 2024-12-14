package ru.otus.webinar.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.otus.webinar.data.notifications
import ru.otus.webinar.databinding.FragmentNotificationsBinding
import ru.otus.webinar.ui.TabsFragmentDirections
import ru.otus.webinar.ui.parentNavController

class NotificationsFragment : Fragment() {

    companion object {
        /**
         * Create a new instance of NotificationFragment.
         */
        fun newInstance(): NotificationsFragment = NotificationsFragment()
    }

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter = NotificationsAdapter(::showNotificationDetails)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notifications.adapter = adapter
        adapter.submitList(notifications.entries.map { it.toPair() })
    }

    private fun showNotificationDetails(notificationId: Int) {
        parentNavController().navigate(TabsFragmentDirections.actionTabsToNotificationDetails(notificationId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}