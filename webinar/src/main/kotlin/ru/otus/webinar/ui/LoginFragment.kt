package ru.otus.webinar.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.otus.webinar.R
import ru.otus.webinar.data.sessionManager
import ru.otus.webinar.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val sessionManager by sessionManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            login.setOnClickListener {
                login()
            }
        }
        setupAlertResult()
    }

    private fun login() = lifecycleScope.launch {
        try {
            sessionManager.login(
                binding.loginInput.text.toString(),
                binding.passwordInput.text.toString()
            )
            findNavController().navigate(R.id.action_login_to_tabs)
        } catch (e: Exception) {
            val message = e.message ?: getString(R.string.error_unknown)
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToErrorDialogFragment(message))
        }
    }

    /**
     * Sets up alert dialog for delete result.
     * https://developer.android.com/guide/navigation/use-graph/programmatic#returning_a_result
     */
    private fun setupAlertResult() {
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.loginFragment)

        val observer = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                if (navBackStackEntry.savedStateHandle.contains(ErrorDialogFragment.CONFIRMATION_RESULT)) {
                    if (true != navBackStackEntry.savedStateHandle.get<Boolean>(ErrorDialogFragment.CONFIRMATION_RESULT)) {
                        findNavController().popBackStack()
                    }
                    navBackStackEntry.savedStateHandle.remove<Boolean>(ErrorDialogFragment.CONFIRMATION_RESULT)
                }
            }
        }

        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}