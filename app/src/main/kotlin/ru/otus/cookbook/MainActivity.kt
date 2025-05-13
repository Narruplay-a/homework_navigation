package ru.otus.cookbook

import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar
import ru.otus.cookbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val fragmentsWithButton = setOf(
        R.id.fragment_recipe,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        onBackPressedDispatcher.addCallback(this) {
            if (!findNavController(R.id.fragment_container_view).popBackStack()) {
                finish()
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val shouldShow = fragmentsWithButton.contains(destination.id)
            toolbar.findViewById<ImageButton>(R.id.deleteButton)?.visibility =
                if (shouldShow) ImageButton.VISIBLE else ImageButton.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container_view)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}