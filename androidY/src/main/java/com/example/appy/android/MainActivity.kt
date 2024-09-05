package com.example.appy.android
import WifiPage
import NavPage
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE
    )

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Some permissions were denied", Toast.LENGTH_SHORT).show()
            }
        }

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(permissionLauncher, requiredPermissions)
                }
            }
        }
    }
}

@Composable
fun MainContent(
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    requiredPermissions: Array<String>
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        checkAndRequestPermissions(context, permissionLauncher, requiredPermissions)
    }

    NavHost(navController = navController, startDestination = "volume_control") {
        composable("volume_control") {
            VolumeControlScreen(navController)
        }
        composable("wifi_page") {
            WifiPage(navController)
        }
        composable("Nav_page") {
            NavPage(navController)
        }
    }
}

@Composable
fun VolumeControlScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        VolumeControlPage()

        Spacer(modifier = Modifier.weight(1f)) // This pushes the button to the bottom

        Button(
            onClick = { navController.navigate("wifi_page") },
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            Text("Go to WiFi Page")
        }

        Button(
            onClick = { navController.navigate("Nav_page") },
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            Text("Go to Nav Page")
        }
    }
}

@Composable
fun VolumeControlPage() {
    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    var mediaVolume by remember { mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)) }
    var ringtoneVolume by remember { mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_RING)) }

    val maxMediaVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    val maxRingtoneVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Media Volume",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = mediaVolume.toFloat(),
            onValueChange = {
                mediaVolume = it.toInt()
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mediaVolume, 0)
            },
            valueRange = 0f..maxMediaVolume.toFloat(),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Ringtone Volume",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Slider(
            value = ringtoneVolume.toFloat(),
            onValueChange = {
                ringtoneVolume = it.toInt()
                audioManager.setStreamVolume(AudioManager.STREAM_RING, ringtoneVolume, 0)
            },
            valueRange = 0f..maxRingtoneVolume.toFloat(),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

private fun checkAndRequestPermissions(
    context: Context,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    requiredPermissions: Array<String>
) {
    val permissionsToRequest = requiredPermissions.filter {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }.toTypedArray()

    if (permissionsToRequest.isNotEmpty()) {
        permissionLauncher.launch(permissionsToRequest)
    } else {
        Toast.makeText(context, "All permissions already granted", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun GreetingPage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}