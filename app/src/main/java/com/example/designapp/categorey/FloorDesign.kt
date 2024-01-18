package com.example.designapp.categorey

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.designapp.Camera
import com.example.designapp.Login.LoginViewModel
import com.example.designapp.R
import com.example.designapp.database.ModelInfo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.storage
import java.io.InputStream
import java.util.UUID


@SuppressLint("StateFlowValueCalledInComposition")

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun FloorDesign(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val contex = LocalContext.current

    var selectedCategory by remember { mutableStateOf("Rangoli")
    }


    val state by viewModel.loginState.collectAsState(initial = null)

    var userEmail by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
//    val state = viewModel.loginState.collectAsState(initial = null)

//    val data by viewModel.data.observeAsState()

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
                text = "Floor Designs",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )
//            Text(text = data.toString())

            var isModalVisible by remember { mutableStateOf(false) }
            // Fetch the current user's email when the component is recomposed
            LaunchedEffect(viewModel) {
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser

                if(currentUser != null) {
                    userEmail = currentUser.email.toString()
                }
            }
//            val isButtonVisible by floorDesignVM.isButtonVisible.collectAsState()

            if (userEmail=="mmadhunicka7@gmail.com" ) {
                OutlinedButton(
                    onClick = {
                        isModalVisible = true
                    }
                ) {
                    Icon(

                        imageVector = Icons.Default.Add,
                        contentDescription = null, // Content description for accessibility
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)

                    )
                }
                if (isModalVisible) {
                    PopupModal(onDismiss = {
                        isModalVisible = false
                    })
                }
            }




            // Show the modal when the button is clicked

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            CategoryTextButton("Rangoli", selectedCategory == "Rangoli") {
                selectedCategory = "Rangoli"
            }
            Spacer(modifier = Modifier.width(16.dp))
            CategoryTextButton("Kolam", selectedCategory == "Kolam") {
                selectedCategory = "Kolam"
            }
            Spacer(modifier = Modifier.width(16.dp))
            CategoryTextButton("Carpet", selectedCategory == "Carpet") {
                selectedCategory = "Carpet"
            }

        }

        // Bottom part of the screen
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            when (selectedCategory) {
                "Rangoli" -> RangoliScreen(navController)
                "Kolam" -> KolamScreen()
                "Carpet" -> CarpetScreen()
            }
        }
    }


}

@Composable
fun CategoryTextButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )



    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RangoliScreen(navController: NavHostController) {
    val contex = LocalContext.current
    val storage = Firebase.storage
    val storageRef = storage.reference.child("uploads") // Replace with your storage path
    val context = LocalContext.current

    // State to hold the list of file names
    var fileNames by remember { mutableStateOf<List<String>>(emptyList()) }
    var fileImages by remember { mutableStateOf<List<String>>(emptyList()) }

    var models by remember {
        mutableStateOf<List<ModelInfo>>(emptyList())
    }

    val database = Firebase.database
    val myRef = database.getReference("Models")
    // Effect to fetch file names when the composable is first drawn
    var modelInfoList by remember { mutableStateOf<List<ModelInfo>>(emptyList()) }
    fun addModelInfoItem(newItem: ModelInfo) {
        modelInfoList = modelInfoList + newItem
    }
    LaunchedEffect(Unit) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                modelInfoList = emptyList()
                val dataList = mutableListOf<String>()
                val dataList2 = mutableListOf<String>()

                for (childSnapshot in snapshot.children) {
                    // Extract the 'name' field from each child

                    val name = childSnapshot.child("modelName").getValue(String::class.java)
                    name?.let { dataList.add(it) }
                    val thumb = childSnapshot.child("downloadUrl").getValue(String::class.java)
                    thumb?.let { dataList2.add(it) }

                    Log.d("asd", childSnapshot.value.toString());


                    val modelName = childSnapshot.child("modelName").getValue(String::class.java)
                    val modelCategory = childSnapshot.child("modelCategorey").getValue(String::class.java)
                    val downloadUrl = childSnapshot.child("downloadUrl").getValue(String::class.java)

                    val modelInfo = ModelInfo(modelName, modelCategory, downloadUrl)
//                    modelInfoList.add(modelInfo)
                    addModelInfoItem(modelInfo)


                }

                // Update the state with the list of names
                fileNames = dataList
                fileImages = dataList2

                // Log the data changes
                Log.d("RangoliScreen", "Data updated: $fileNames")

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
                Log.e("RangoliScreen", "Error fetching data: ${error.message}")
            }
        })




//        storageRef.listAll()
//            .addOnSuccessListener { result ->
//                fileNames = result.items.map { it.name }
////
//            }
//            .addOnFailureListener { exception ->
//                // Handle errors
//                Log.e("RangoliScreen", "Error fetching file names: $exception")
//            }
    }
    // Function to navigate to a new activity and pass data


//    Text("Rangoli Screen Content")
    Column(
        modifier = Modifier

            .padding(16.dp)
            .size(600.dp)
            .verticalScroll(rememberScrollState())
    )
    {




        Text("Rangoli Screen Content")


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            modelInfoList.forEach { modelInfo ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {

//                            val context = LocalContext.current

                            // Create an Intent to launch the new activity
                            val intent = Intent(context, Camera::class.java)

                            // Pass data to the new activity using Intent extras
                            intent.putExtra("ModelName", modelInfo.ModelName)
                            intent.putExtra("DownloadUrl", modelInfo.downloadUrl)

                            // Launch the new activity
                            context.startActivity(intent)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        // Display Image
                        Image(
                            painter = rememberImagePainter(modelInfo.downloadUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.secondary)

                        )

                        // Display Model Info Text
                        Text(
                            text = "Model Name: ${modelInfo.ModelName}",
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(4.dp)
                        )

//                        Text(
//                            text = "Download URL: ${modelInfo.downloadUrl}",
//                            color = Color.White,
//                            modifier = Modifier
//                                .align(Alignment.Start)
//                                .padding(4.dp)
//                        )
                    }
                }
            }
        }

        }



    }




//}

@Composable
fun FileItem(fileName: String) {
    // Modify this function to display each file item as needed
    // You can use the fileName to create a clickable item or any other UI representation
    Text(text = fileName, modifier = Modifier.clickable {
        // Handle item click (e.g., open file, perform action, etc.)
    })
}

@Composable
fun KolamScreen() {
    Text("Kolam Screen Content")
}

@Composable
fun CarpetScreen() {
    Text("Carpet Screen Content")
}


@Composable
fun rememberLauncher(onResult: (Uri?) -> Unit): ActivityResultLauncher<String> {
    return rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onResult(uri)
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PopupModal(onDismiss: () -> Unit) {

    var selectedFile: InputStream? by remember {
        mutableStateOf(null)
    }

    val contex = LocalContext.current
    val categories = listOf("Category1", "Category2", "Category3")
    val database = Firebase.database
    val myRef = database.getReference("Models")
    var ModelName by remember {
        mutableStateOf("")
    }
    var ModelCategorey by remember {
        mutableStateOf("")
    }
    var ModelImage by remember {
        mutableStateOf("")
    }
    var categoryOptions by remember {
        mutableStateOf(
            listOf("Floor", "Wall")
        )
    }
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),


        ) {

        Surface(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(size = 16.dp),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = {
                        onDismiss()
                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .offset(y = 15.dp, x = 100.dp)

                        .background(

                            color = Color.Gray,
                            shape = CircleShape,

                            )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }


                Spacer(modifier = Modifier.padding(10.dp))
                androidx.compose.material.OutlinedTextField(value = ModelName,
                    onValueChange = { ModelName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    placeholder = {
                        Text(text = "Enter Model Name", fontSize = 14.sp)
                    }

                )




                Spacer(modifier = Modifier.padding(10.dp))


//                androidx.compose.material.OutlinedTextField(value = ModelCategorey,
//                    onValueChange = { ModelCategorey = it },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(10.dp),
//                    placeholder = {
//                        Text(text = "Enter Model Categorey", fontSize = 14.sp)
//                    }
//
//                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable {
                            // Expand the custom dropdown
                            expanded = !expanded
                        }
                ) {
                    BasicTextField(
                        value = categoryOptions[selectedCategoryIndex],
                        onValueChange = {},
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .clickable { expanded = !expanded }
                            .border(1.dp, MaterialTheme.colorScheme.primary)
                            .fillMaxWidth()
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { expanded = !expanded }
                    )
                }

                if (expanded) {
                    Dialog(onDismissRequest = { expanded = false }) {
                        Column {
                            categoryOptions.forEachIndexed { index, option ->
                                Text(
                                    text = option,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clickable {
                                            selectedCategoryIndex = index
                                            expanded = false
                                        }
                                )
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.padding(10.dp))

                val launcher =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                        uri?.let { selectedUri ->
                            // Handle the selected file URI
                            val contentResolver = contex.contentResolver
                            selectedFile = contentResolver.openInputStream(selectedUri)
                        }
                    }

                Button(
                    onClick = {
                        // Launch the file picker
                        launcher.launch("*/*")
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Select File")
                }



                Row {
                    OutlinedButton(
                        onClick = {
                            if (ModelName.isNotEmpty()  && selectedFile != null) {
                                val storage = Firebase.storage
                                val storageRef =
                                    storage.reference.child("uploads").child("${UUID.randomUUID()}.glb")
                                val metadata = StorageMetadata.Builder().setContentType("application/octet-stream").build()
                                val uploadTask = storageRef.putStream(selectedFile!!,metadata)
                                val selectedModelCategory = categoryOptions[selectedCategoryIndex]

                                uploadTask.addOnSuccessListener { taskSnapshot ->
                                    // File uploaded successfully, get the download URL
                                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                        // Now you can use the download URL to store it in your database or perform other actions
                                        val downloadUrl = downloadUri.toString()

                                        // Save downloadUrl along with ModelName and ModelCategorey to Firebase Realtime Database
                                        val modelInfo =
                                            ModelInfo(ModelName, selectedModelCategory, downloadUrl)

                                        val uniqueKey = myRef.push().key

                                        myRef.child(ModelName).setValue(modelInfo).addOnSuccessListener {
                                            ModelName = ""
//                                            ModelCategorey = ""
                                            Toast.makeText(contex, "Successfully Added", Toast.LENGTH_SHORT).show()
                                        }.addOnFailureListener {
                                            Toast.makeText(contex, it.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(contex, "File upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            }

                            else {
                                Toast.makeText(
                                    contex,
                                    "Please enter value first",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },

                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.text_medium),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .offset(y = -5.dp)
                    ) {
                        Text(text = "Insert", fontSize = 16.sp, color = Color.White)
                    }


                }
            }


        }
    }
}
