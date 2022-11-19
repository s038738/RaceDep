@file:OptIn(ExperimentalSnapperApi::class)

package com.example.wheelz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wheelz.R
import com.example.wheelz.activities.main.ui.AppBar
import com.example.wheelz.activities.main.ui.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import dev.chrisbanes.snapper.ExperimentalSnapperApi

class MainActivity : AppCompatActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.email.toString()
        } else {
            currentUserID = ""
        }
        setContent {
            Toolbar(currentUserID)

        }

    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun Toolbar(currentUserID: String) {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        var items1 = listOf<MenuItem>()
        if (currentUserID == "") {
            items1 = listOf(
                MenuItem(
                    id = "Sign in",
                    title = resources.getString(R.string.sign_in),
                    contentDescription = "Sign in",
                    icon = Icons.Default.Person
                ),
                MenuItem(
                    id = "Home",
                    title = resources.getString(R.string.home),
                    contentDescription = "Go to home screen",
                    icon = Icons.Default.Home
                ),
                MenuItem(
                    id = "Calendar",
                    title = resources.getString(R.string.calendar),
                    contentDescription = "Calendar",
                    icon = Icons.Default.List
                ),
                MenuItem(
                    id = "Cars",
                    title = resources.getString(R.string.cars),
                    contentDescription = "Cars",
                    icon = Icons.Default.Star
                ),
                MenuItem(
                    id = "Settings",
                    title = resources.getString(R.string.settings),
                    contentDescription = "Go to settings screen",
                    icon = Icons.Default.Settings
                ),
                MenuItem(
                    id = "Help",
                    title = resources.getString(R.string.help),
                    contentDescription = "please",
                    icon = Icons.Default.Info
                ),
            )
        } else {

            items1 = listOf(
                MenuItem(
                    id = "Sign out",
                    title = resources.getString(R.string.sign_out),
                    contentDescription = "Sign out",
                    icon = Icons.Default.ExitToApp
                ),
                MenuItem(
                    id = "Home",
                    title = resources.getString(R.string.home),
                    contentDescription = "Go to home screen",
                    icon = Icons.Default.Home
                ),
                MenuItem(
                    id = "Calendar",
                    title = resources.getString(R.string.calendar),
                    contentDescription = "Calendar",
                    icon = Icons.Default.List
                ),
                MenuItem(
                    id = "Cars",
                    title = resources.getString(R.string.cars),
                    contentDescription = "Cars",
                    icon = Icons.Default.Star
                ),
                MenuItem(
                    id = "Settings",
                    title = resources.getString(R.string.settings),
                    contentDescription = "Go to settings screen",
                    icon = Icons.Default.Settings
                ),
                MenuItem(
                    id = "Help",
                    title = resources.getString(R.string.help),
                    contentDescription = "please",
                    icon = Icons.Default.Info
                ),

                )
        }
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppBar(
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                DrawerHeader(currentUserID)
                DrawerBody(
                    items =
                    items1,
                    onItemClick = {
                        println("Clicked on ${it.title}")
                        when (it.id) {
                            "Sign in" -> {
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                //finish()
                            }
                            "Sign out" -> {
                                FirebaseAuth.getInstance().signOut()
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                                finish()
                            }
                            "Home" -> {
                                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                                finish()
                            }
                            "Cars" -> {
                                startActivity(Intent(this@MainActivity, CarActivity::class.java))
                                finish()
                            }
                            "Calendar" -> {
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        CalendarActivity::class.java
                                    )
                                )
                                finish()
                            }

                        }
                    }
                )
            }
        ) {
            titleHeader()
        }
    }


    @Composable
    fun titleHeader() {
        val scrollState = rememberScrollState()
        var offset by remember { mutableStateOf(0f) }

        Column(
            modifier = Modifier
                .scrollable(
                    orientation = Orientation.Vertical,
                    state = rememberScrollableState { delta ->
                        offset += delta
                        delta
                    })
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp - (-offset).dp),
                //contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "",
                    modifier = Modifier
                        .height(500.dp), contentScale = ContentScale.Crop
                )
                Text(
                    text = resources.getString(R.string.race_rental_nurburgring),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState, offset < -40),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5,
                    color = Color(0xFFFFFFFF)
                )
                Text(
                    resources.getString(R.string.desc_home_rental_company),
                    Modifier
                        .padding(20.dp, 80.dp, 20.dp, 5.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,
                    color = Color(0xFFFFFFFF)
                )
            }
            Column() {
                Text(
                    resources.getString(R.string.your_safety),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .verticalScroll(scrollState, offset < -40),
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    resources.getString(R.string.desc_home_rental_company2),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .verticalScroll(scrollState, offset < -40),
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    resources.getString(R.string.all_inclusive),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .verticalScroll(scrollState, offset < -40),
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    resources.getString(R.string.desc_home_rental_company3),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .verticalScroll(scrollState, offset < -40),
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.body2
                )
                Text(
                    resources.getString(R.string.flexible_booking_dates),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .verticalScroll(scrollState, offset < -40),
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    resources.getString(R.string.desc_home_rental_company4),
                    Modifier
                        .padding(20.dp, 10.dp, 20.dp, 5.dp)
                        .verticalScroll(scrollState, offset < -40),
                    lineHeight = 20.sp,
                    style = MaterialTheme.typography.body2
                )
                Button(
                    onClick = {
                        startActivity(Intent(this@MainActivity, CarActivity::class.java))
                        finish()
                        //TODO KAROLI <3//
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier
                        .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                        .verticalScroll(scrollState, offset < -40),
                ) {
                    Text(
                        resources.getString(R.string.browse_cars),
                        Modifier
                            .padding()
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color(0xFFFFFFFF),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
    }
}
