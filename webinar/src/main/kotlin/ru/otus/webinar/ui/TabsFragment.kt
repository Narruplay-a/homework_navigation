package ru.otus.webinar.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.otus.webinar.R
import ru.otus.webinar.data.sessionManager
import ru.otus.webinar.databinding.FragmentTabsBinding
import ru.otus.webinar.ui.dashboard.DashboardFragment
import ru.otus.webinar.ui.home.HomeFragment
import ru.otus.webinar.ui.notifications.NotificationsFragment

class TabsFragment : Fragment() {
    private var _binding: FragmentTabsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var homeFragment: Fragment
    private lateinit var dashboard: Fragment
    private lateinit var notifications: Fragment

    private val fragments: List<Fragment> get() = listOf(
        homeFragment,
        dashboard,
        notifications
    )

    private var selectedIndex = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragments(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        binding.topAppBar.title = binding.navView.menu.getItem(selectedIndex).title
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

    @SuppressLint("CommitTransaction")
    private fun setupFragments(savedInstanceState: Bundle?) {
        if (null == savedInstanceState) {
            homeFragment = HomeFragment.newInstance()
            dashboard = DashboardFragment.newInstance()
            notifications = NotificationsFragment.newInstance()

            childFragmentManager.commit {
                add(R.id.tabs, homeFragment, HomeFragment::class.java.name)
                add(R.id.tabs, dashboard, DashboardFragment::class.java.name)
                add(R.id.tabs, notifications, NotificationsFragment::class.java.name)
                selectFragment(selectedIndex)
            }
        } else {
            homeFragment = requireNotNull(childFragmentManager.findFragmentByTag(HomeFragment::class.java.name))
            dashboard = requireNotNull(childFragmentManager.findFragmentByTag(DashboardFragment::class.java.name))
            notifications = requireNotNull(childFragmentManager.findFragmentByTag(NotificationsFragment::class.java.name))
        }
    }

    private fun setupNavigation() = with(binding) {
        navView.setOnItemSelectedListener { item ->
            topAppBar.title = item.title
            when(item.itemId) {
                R.id.navigation_home -> {
                    selectFragment(0)
                    true
                }
                R.id.navigation_dashboard -> {
                    selectFragment(1)
                    true
                }
                R.id.navigation_notifications -> {
                    selectFragment(2)
                    true
                }
                else -> false
            }
        }
    }

    private fun FragmentTransaction.selectFragment(selectedIndex: Int): FragmentTransaction {
        fragments.forEachIndexed { index, fragment ->
            if (index == selectedIndex) {
                attach(fragment)
            } else {
                detach(fragment)
            }
        }

        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        return this
    }

    private fun selectFragment(indexToSelect: Int) {
        this.selectedIndex = indexToSelect
        childFragmentManager.beginTransaction()
            .selectFragment(indexToSelect)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_INDEX, selectedIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SELECTED_INDEX = "selectedIndex"
    }
}