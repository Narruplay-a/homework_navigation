package ru.otus.webinar.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.otus.webinar.R
import ru.otus.webinar.data.notifications
import ru.otus.webinar.databinding.FragmentNotificationsBinding

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
        requireParentFragment().parentFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, NotificationDetailsFragment.newInstance(notificationId))
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}