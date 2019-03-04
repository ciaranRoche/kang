package org.ciaranroche.kang.activities

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import org.ciaranroche.kang.R
import org.ciaranroche.kang.main.MainApp

class StartUpActivity : AppCompatActivity() {
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_up)

        app = application as MainApp

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_startup_fragment) as NavHostFragment

        val startupController = host.navController

        startupController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            Log.d("StartUpActivity", "Navigated to $dest")
        }
    }
}
