package ru.otus.webinar.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.otus.webinar.R
import ru.otus.webinar.data.sessionManager
import ru.otus.webinar.databinding.FragmentDashboardBinding
import ru.otus.webinar.ui.WelcomeFragment

class DashboardFragment : Fragment() {

    companion object {
        /**
         * Create a new instance of DashboardFragment.
         */
        fun newInstance(): DashboardFragment = DashboardFragment()
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val sessionManager by sessionManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener {
            logout()
        }
    }

    private fun logout() = lifecycleScope.launch {
        sessionManager.logout()
        requireParentFragment().parentFragmentManager.commit {
            replace(R.id.fragment_container_view, WelcomeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}