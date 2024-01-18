package com.example.designapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.designapp.R
import com.example.designapp.ui.theme.DesignAppTheme

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp)
            )
        Text("Welcome to the Home Screen", fontWeight = FontWeight.Bold, fontSize = 24.sp,
            textAlign = TextAlign.Center
            )
        Spacer(modifier = Modifier.height(16.dp))
        Text("You have successfully logged in!", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        GradientButton("Floor Design",navController



            )
        Spacer(modifier = Modifier.height(16.dp))
        GradientButton("Wall Design",navController)

    }
}

@Composable
fun GradientButton(text: String, navController: NavHostController) {
    val startColor = Color(0xFFDAA520) // Dark Goldenrod
    val endColor = Color(0xFFFFD700)   // Gold

    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(startColor, endColor)
    )

    Button(
        onClick = {
                  if(text == "Floor Design") {
                      navController.navigate("floor")
                  }
                  if(text == "Wall Design") {
                      navController.navigate("wall")
                  }
        },

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.text_medium),
            contentColor = Color.White
        ),


        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            ,

        shape = RoundedCornerShape(size= 16.dp),
        elevation = ButtonDefaults.buttonElevation()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.Bold)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    DesignAppTheme {
//        val navController = rememberNavController();
//        HomeScreen(navController)
//    }
//}