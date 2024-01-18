
package com.example.designapp.categorey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designapp.ui.theme.DesignAppTheme

class WallCategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignAppTheme {

                WallArtScreen()
            }
        }
    }
}

@Composable
fun WallDesign() {
    var selectedCategory by remember { mutableStateOf("Frame") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Wall Designs",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            CategoryTextButton("Frame", selectedCategory == "Frame") {
                selectedCategory = "Frame"
            }
            Spacer(modifier = Modifier.width(16.dp))
            CategoryTextButton("WallArt", selectedCategory == "WallArt") {
            }
        }

        // Bottom part of the screen
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            when (selectedCategory) {
                "Frame" -> FrameScreen()
                "WallArt" -> WallArtScreen()
            }
        }
    }
}


@Composable
fun FrameScreen() {

    Text("Frame Screen Content")
}

@Composable
fun WallArtScreen() {
    Text("Wall Art Screen Content")
}


@Preview(showBackground = true)
@Composable
fun WallDesignPreview() {
    DesignAppTheme {
        WallArtScreen()
    }
}




























//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.graphics.ColorUtils
//import com.example.designapp.ui.theme.DesignAppTheme
//
//@Composable
//fun FloorDesign() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Welcome to the Home Screen", fontWeight = FontWeight.Bold, fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("You have successfully logged in!", fontSize = 16.sp, color = Color.Gray)
//        Spacer(modifier = Modifier.height(32.dp))
//        GradientButton("Rangoli")
//        Spacer(modifier = Modifier.height(16.dp))
//        GradientButton("Kolam")
//        Spacer(modifier = Modifier.height(16.dp))
//        GradientButton("Carpet")
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun FloorDesignPreview() {
//    DesignAppTheme {
//        FloorDesign()
//    }
//}















//package com.example.designapp.categorey
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.graphics.ColorUtils
//import com.example.designapp.ui.theme.DesignAppTheme
//
//@Composable
//fun WallDesign() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Welcome to the Home Screen", fontWeight = FontWeight.Bold, fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("You have successfully logged in!", fontSize = 16.sp, color = Color.Gray)
//        Spacer(modifier = Modifier.height(32.dp))
//        GradientButton("Frame")
//        Spacer(modifier = Modifier.height(16.dp))
//        GradientButton("Wall Art")
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun WallDesignPreview() {
//    DesignAppTheme {
//        WallDesign()
//    }
//}