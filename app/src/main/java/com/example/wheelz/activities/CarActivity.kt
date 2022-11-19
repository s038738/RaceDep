package com.example.wheelz.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wheelz.R
import com.example.wheelz.activities.main.ui.AppBar
import com.example.wheelz.activities.main.ui.MenuItem
import com.example.wheelz.models.Car
import com.google.firebase.auth.FirebaseAuth
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch

class CarActivity : AppCompatActivity() {
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
    @OptIn(ExperimentalSnapperApi::class)
    @Composable
    fun MyList(currentUserID: String) {
        Row(
            modifier = Modifier
                .padding(top = 5.dp)
        ) {
            val carlist = listOf(
                Car(
                    id = "01",
                    car_class = resources.getString(R.string.car_class_elektro),
                    engine = resources.getString(R.string.engine_e),
                    gearbox = resources.getString(R.string.gearbox_1_speed),
                    title = "TESLA Model 3 P",
                    image = "https://www.ringfreaks.de/uploads/cars/model-3-p--220407095401.png",
                    weight = "1750kg",
                    year = "2022",
                    desc = resources.getString(R.string.desc_tesla)
                ),
                Car(
                    id = "02",
                    car_class = "VT2",
                    engine = resources.getString(R.string.engine_230hp),
                    gearbox = resources.getString(R.string.gearbox_6_speed_auto),
                    title = "VW Golf GTI",
                    image = "https://www.ringfreaks.de/uploads/cars/vw-golf-200107101200.png",
                    weight = "1300kg",
                    year = "2019",
                    desc = resources.getString(R.string.desc_vw)
                ),
                Car(
                    id = "03",
                    car_class = "VT2",
                    engine = resources.getString(R.string.engine_245),
                    gearbox = resources.getString(R.string.gearbox_8_speed_auto),
                    title = "BMW F30 328i",
                    image = "https://www.ringfreaks.de/uploads/cars/328i-210824102010.png",
                    weight = "1400kg",
                    year = "2021",
                    desc = resources.getString(R.string.desc_f30_328i)
                ),
                Car(
                    id = "04",
                    car_class = "V5/RS5",
                    engine = resources.getString(R.string.engine_270),
                    gearbox = resources.getString(R.string.gearbox_6_speed_manual),
                    title = "BMW E90 330i",
                    image = "https://www.ringfreaks.de/uploads/cars/330i-200107101806.png",
                    weight = "1350kg",
                    year = "2019",
                    desc = resources.getString(R.string.desc_e90_330i)
                ),
                Car(
                    id = "05",
                    car_class = "V4/RS4",
                    engine = resources.getString(R.string.engine_225),
                    gearbox = resources.getString(R.string.gearbox_6_speed_manual),
                    title = "BMW E90 325i",
                    image = "https://www.ringfreaks.de/uploads/cars/bmw-e90-200107101631.png",
                    weight = "1350kg",
                    year = "2019",
                    desc = resources.getString(R.string.desc_e90_325i)
                ),

                )
            val lazyListState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                state = lazyListState,
                flingBehavior = rememberSnapperFlingBehavior(lazyListState),
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    bottom = 15.dp,
                    end = 0.dp
                ),
            ) {

                items(carlist) { item ->
                    MyCard(item, currentUserID)
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }

    @Composable
    fun MyCard(car: Car, currentUserID: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                //.height(500.dp),
                .fillMaxHeight(),

            elevation = 0.dp,
            //backgroundColor = Color(0xFFFFFFFF),

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                    //.background(Color.Black)
                    //contentAlignment = Alignment.Center,
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = Uri.parse(car.image)
                        ),
                        contentDescription = "123",
                        modifier = Modifier.size(360.dp),
                        contentScale = ContentScale.FillWidth,

                    )
                    Text(
                        text = "____________________",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 260.dp,
                                start = 35.dp
                            ),
                        color = Color.Black,
                        fontSize = 30.sp,

                        )
                    Text(
                        text = car.title!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 10.dp,
                                start = 5.dp
                            ),
                        fontSize = 30.sp,
                        )
                    Text(
                        text = car.desc!!,

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 300.dp,
                                bottom = 5.dp,
                                start = 5.dp
                            ),
                        fontSize = 15.sp,
                    )
                    Text(
                        //text = "Class: ${car.car_class!!}",
                        text = "${resources.getString(R.string.class_ignore)}: ${car.car_class!!}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 410.dp,
                                bottom = 5.dp,
                                start = 5.dp
                            ),
                        fontSize = 15.sp,
                    )
                    Text(
                        text = "${resources.getString(R.string.engine)}: ${car.engine!!}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 425.dp,
                                bottom = 5.dp,
                                start = 5.dp
                            ),
                        fontSize = 15.sp,

                        )
                    Text(
                        text = "${resources.getString(R.string.gearbox)}: ${car.gearbox!!}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 440.dp,
                                bottom = 5.dp,
                                start = 5.dp
                            ),
                        fontSize = 15.sp,

                        )
                    Text(
                        text = "${resources.getString(R.string.weight)}: ${car.weight!!}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 455.dp,
                                bottom = 5.dp,
                                start = 5.dp
                            ),
                        fontSize = 15.sp,

                        )
                    Text(
                        text = "${resources.getString(R.string.year)}: ${car.year!!}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 470.dp,
                                bottom = 5.dp,
                                start = 5.dp
                            ),
                        fontSize = 15.sp,

                        )
                    Button(
                        onClick = {
                                  buttonClick(currentUserID = currentUserID)
                            //TODO KAROLI <3//
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 450.dp,
                                bottom = 5.dp,
                                start = 270.dp
                            )
                    )

                    {
                        Text(text = resources.getString(R.string.order), color = Color.White)

                    }
                }
            }
        }
    }

    fun buttonClick(currentUserID: String) {
        if (currentUserID == ""){
            Toast.makeText(
                this@CarActivity,
                resources.getString(R.string.you_need_to_register_first),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            startActivity(Intent(this@CarActivity, CalendarActivity::class.java))
            finish()
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
                                startActivity(Intent(this@CarActivity, LoginActivity::class.java))
                                //finish()
                            }
                            "Sign out" -> {
                                FirebaseAuth.getInstance().signOut()
                                startActivity(Intent(this@CarActivity, LoginActivity::class.java))
                                finish()
                            }
                            "Home" -> {
                                startActivity(Intent(this@CarActivity, MainActivity::class.java))
                                finish()
                            }
                            "Cars" -> {
                                startActivity(Intent(this@CarActivity, CarActivity::class.java))
                                finish()
                            }
                            "Calendar" -> {
                                startActivity(Intent(this@CarActivity, CalendarActivity::class.java))
                                finish()
                            }
                        }

                    }
                )
            }

        ) {

            Card() {
//                Image(
//                    painter = painterResource(id = R.drawable.gradient),
//                    contentDescription = "",
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                    )
                MyList(currentUserID)
            }

        }
    }


}