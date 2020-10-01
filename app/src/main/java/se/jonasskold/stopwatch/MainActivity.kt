package se.jonasskold.stopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import kotlinx.coroutines.ObsoleteCoroutinesApi
import se.jonasskold.stopwatch.stopwatch.StopwatchScreen

class MainActivity : AppCompatActivity() {
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchScreen()
        }
    }
}