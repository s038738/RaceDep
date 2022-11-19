package com.example.wheelz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.wheelz.R
import com.example.wheelz.activities.main.ui.AppBar
import com.example.wheelz.activities.main.ui.MenuItem
import com.example.wheelz.firestore.FireStoreClass
import com.example.wheelz.models.Orders
import com.example.wheelz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firestore.v1.StructuredQuery.Order
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarActivity : AppCompatActivity() {
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
                    title =resources.getString(R.string.home),
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
                                startActivity(Intent(this@CalendarActivity, LoginActivity::class.java))
                                //finish()
                            }
                            "Sign out" -> {
                                FirebaseAuth.getInstance().signOut()
                                startActivity(Intent(this@CalendarActivity, LoginActivity::class.java))
                                finish()
                            }
                            "Home" -> {
                                startActivity(Intent(this@CalendarActivity, MainActivity::class.java))
                                finish()
                            }
                            "Cars" -> {
                                startActivity(Intent(this@CalendarActivity, CarActivity::class.java))
                                finish()
                            }
                            "Calendar" -> {
                                startActivity(Intent(this@CalendarActivity, CalendarActivity::class.java))
                                finish()
                            }
                        }
                    }
                )
            }

        ) {
            Card {

                Image(
                    painter = painterResource(id = R.drawable.home_screen),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                var date by remember {
                    mutableStateOf("")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    AndroidView(factory = { CalendarView(it) }, update = {
                        it.setOnDateChangeListener{ calendarView, year, month, day ->
                            //date = "$day - ${month + 1} - $year"
                            date = "$year - ${month + 1} - $day"
                        }
                    })
                    Text(
                        text = date,
                        style = MaterialTheme.typography.h5,

                        )

                    val listItems = arrayOf(
                        "TESLA Model 3 P",
                        "VW Golf GTI",
                        "BMW F30 328i",
                        "BMW E90 330i",
                        "BMW E90 325i"
                    )
                    val contextForToast = LocalContext.current.applicationContext
                    var expanded by remember {
                        mutableStateOf(false)
                    }
                    var disabledItem by remember {
                        mutableStateOf(-1)
                    }
                    var selected by remember {
                        mutableStateOf(resources.getString(R.string.select_a_car))
                    }
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                expanded = true
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                        ) {
                            Text(
                                text = "$selected  ",
                                color = Color(0xFFFFFFFF),
                                style = MaterialTheme.typography.h5
                            )
                            Icon(imageVector = Icons.Default.ArrowDropDownCircle, contentDescription = "Open options", tint = Color(0xFFFFFFFF))
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            // adding items
                            listItems.forEachIndexed { itemIndex, itemValue ->
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        disabledItem = itemIndex
                                        selected = itemValue
                                    },
                                    enabled = (itemIndex != disabledItem)
                                ) {
                                    Text(text = itemValue)
                                }
                            }
                        }
                    }

                    Button( modifier = Modifier.padding(top = 10.dp),
                        onClick = {
                            if (currentUserID == ""){
                                Toast.makeText(
                                    this@CalendarActivity,
                                    resources.getString(R.string.you_need_to_register_first),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (date == "") {
                                Toast.makeText(
                                    this@CalendarActivity,
                                    resources.getString(R.string.you_need_to_set_date_first),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (selected == resources.getString(R.string.select_a_car)) {
                                Toast.makeText(
                                    this@CalendarActivity,
                                    resources.getString(R.string.you_need_to_select_a_car_first),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else {
                                val order = Orders(
                                    UUID.randomUUID().toString(),
                                    date,
                                    selected,
                                    currentUserID
                                )
                                FireStoreClass().registerOrder(this@CalendarActivity, order)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    ) {
                        Text(
                            text = resources.getString(R.string.book_now),
                            color = Color(0xFFFFFFFF),
                            style = MaterialTheme.typography.h5
                        )
                    }

                }

            }

        }
    }


    fun userOrderSuccess() {
        Toast.makeText(
            this@CalendarActivity,
            resources.getString(R.string.your_order_is_complete),
            Toast.LENGTH_SHORT
        ).show()
    }







}