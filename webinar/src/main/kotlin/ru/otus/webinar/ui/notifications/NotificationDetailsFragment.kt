package ru.otus.webinar.ui.notifications

import android.Manifest.permission.POST_NOTIFICATIONS
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.otus.webinar.Notifications
import ru.otus.webinar.R
import ru.otus.webinar.data.notifications
import ru.otus.webinar.databinding.FragmentNotificationDetailsBinding

class NotificationDetailsFragment : Fragment() {
    private val notificationId: Int get() = NotificationDetailsFragmentArgs.fromBundle(requireArguments()).notificationId
    private val notification: String get() = notifications.getValue(notificationId)

    private lateinit var binding: FragmentNotificationDetailsBinding

    private val notificationsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            createNotification()
        }
    }

    private fun createNotification() {
        val context = requireContext()
        val controller = findNavController()
        val pendingIntent = controller.createDeepLink()
            .setDestination(R.id.notificationDetailsFragment)
            .setArguments(NotificationDetailsFragmentArgs(notificationId).toBundle())
            .createPendingIntent()

        Notifications.buildNotification(context, notificationId, notification, pendingIntent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNotificationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.title.text = notification
        binding.topAppBar.title = notification
        binding.createNotification.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationsLauncher.launch(POST_NOTIFICATIONS)
            }
        }
    }
}