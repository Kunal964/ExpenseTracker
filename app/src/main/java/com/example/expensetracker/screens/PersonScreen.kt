package com.example.expensetracker.screens

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.widget.ExpenseTextView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PersonScreen(navController : NavController) {
    Scaffold() {
        Surface(modifier = Modifier.fillMaxSize()) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (nameRow, list, card, topBar) = createRefs()

                // Top Bar
                Image(
                    painter = painterResource(id = R.drawable.ic_topbar),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
                Image(painter = painterResource(id = R.drawable.ic_top), contentDescription = null)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    Image(painter = painterResource(id = R.drawable.chevron_left), contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .clickable {
                                navController.navigate("bottomBar")
                            })
                    ExpenseTextView(
                        text = "Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                    Image(painter = painterResource(id = R.drawable.ic_notification), contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd))
                }
                Column(
                    modifier = Modifier.constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                {
                    Image(painter = painterResource(id = R.drawable.person_image), contentDescription = "Person",
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .size(100.dp))
                }
                PersonInformation(modifier = Modifier
                    .padding(top = 10.dp)
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}



@Composable
fun PersonInformation(modifier: Modifier) {
    Column(modifier = modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .shadow(25.dp)
        .clip(RoundedCornerShape(19.dp))
        .background(Color.White)
        .padding(16.dp)
        .verticalScroll(rememberScrollState())
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.user_fill), contentDescription = "User" )
            Spacer(modifier = Modifier.padding(15.dp))
            ExpenseTextView(text = "Account info", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.padding(13.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.users), contentDescription = "Users" )
            Spacer(modifier = Modifier.padding(15.dp))
            ExpenseTextView(text = "Personal Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            
        }
        Spacer(modifier = Modifier.padding(13.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.envelope), contentDescription = "Users" )
            Spacer(modifier = Modifier.padding(15.dp))
            ExpenseTextView(text = "Message Center", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }
        Spacer(modifier = Modifier.padding(13.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.shield), contentDescription = "Users" )
            Spacer(modifier = Modifier.padding(15.dp))
            ExpenseTextView(text = "Login and Security", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }
        Spacer(modifier = Modifier.padding(13.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painter = painterResource(id = R.drawable.lock_key), contentDescription = "Users" )
            Spacer(modifier = Modifier.padding(15.dp))
            ExpenseTextView(text = "Data and Privacy", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        }
    }
}

@Preview
@Composable
fun PresonScreenPreview() {
    PersonScreen(rememberNavController())
}