package ru.otus.webinar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.launch
import ru.otus.webinar.R
import ru.otus.webinar.data.sessionManager
import ru.otus.webinar.databinding.FragmentTabsBinding

class TabsFragment : Fragment() {
    private var _binding: FragmentTabsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val session by sessionManager()

    private fun logout() {
        lifecycleScope.launch {
            session.logout()
            findNavController().navigate(R.id.action_tabs_to_welcome)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupNavigation() = with(binding) {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.tabs) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
            )
        )
        topAppBar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}