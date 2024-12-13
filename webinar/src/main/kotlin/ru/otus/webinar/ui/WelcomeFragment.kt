package ru.otus.webinar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.otus.webinar.R
import ru.otus.webinar.data.Session
import ru.otus.webinar.data.sessionManager
import ru.otus.webinar.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val sessionManager by sessionManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                when (sessionManager.getSession()) {
                    is Session.Active -> {
                        parentFragmentManager.commit {
                            replace(R.id.fragment_container_view, TabsFragment())
                        }
                    }
                    else -> {
                        parentFragmentManager.commit {
                            replace(R.id.fragment_container_view, LoginFragment())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}