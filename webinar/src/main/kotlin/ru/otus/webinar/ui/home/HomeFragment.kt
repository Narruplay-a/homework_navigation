package ru.otus.webinar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.otus.webinar.R
import ru.otus.webinar.data.getHome
import ru.otus.webinar.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    companion object {
        /**
         * Create a new instance of HomeFragment.
         */
        fun newInstance(): HomeFragment = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory(getHome())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = getString(R.string.home_fragment, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}