package org.ciaranroche.kang.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.ciaranroche.kang.R
import org.jetbrains.anko.intentFor

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivityForResult(intentFor<StartUpActivity>(), 0)
        }, 1500)
    }
}