import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun NavPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 30m ahead with an arrow and bold, green style
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "30 m ahead",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 32.sp,
                    color = Color.Green
                ),
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                painter = painterResource(id = com.example.appy.android.R.drawable.baseline_arrow_upward_24),
                contentDescription = "Arrow Icon",
                tint = Color.Green,
                modifier = Modifier.size(40.dp)
            )
        }

        // Railway platform rectangle
        Box(
            modifier = Modifier
                .width(350.dp) // Wider rectangle
                .height(700.dp) // Taller rectangle
                .background(Color.LightGray, shape = RoundedCornerShape(60.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Platform 1 (outside the rectangle)
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Platform 1",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                        color = Color.Black,
                        modifier = Modifier.rotate(270f)
                    )
                }

                // WiFi icons and Bandra Station inside the rectangle
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = com.example.appy.android.R.drawable.baseline_wifi_24),
                        contentDescription = "WiFi Icon 1",
                        modifier = Modifier.size(32.dp), // Smaller icons
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Icon(
                        painter = painterResource(id = com.example.appy.android.R.drawable.baseline_wifi_24),
                        contentDescription = "WiFi Icon 2",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.height(48.dp))
                    Icon(
                        painter = painterResource(id = com.example.appy.android.R.drawable.baseline_wifi_24),
                        contentDescription = "WiFi Icon 3",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Black
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Text(
                        text = "Bandra Station",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                        color = Color.Black
                    )
                }

                // Platform 3 (outside the rectangle)
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp)
                ) {
                    Text(
                        text = "Platform 3",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                        color = Color.Black,
                        modifier = Modifier.rotate(90f)
                    )
                }
            }
        }
    }
}

