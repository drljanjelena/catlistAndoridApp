package rs.raf.rmaprojekat1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import rs.raf.rmaprojekat1.core.theme.RMAProjekat1Theme
import rs.raf.rmaprojekat1.navigation.CatsNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RMAProjekat1Theme {
                Log.d("TEST", "1.PORUKA DA JE USAO U MAIN")
                CatsNavigation();
            }
        }
    }
}
