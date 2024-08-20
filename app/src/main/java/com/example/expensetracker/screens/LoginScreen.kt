package com.example.expensetracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensetracker.R
import com.example.expensetracker.components.ClickableLoginTextComponent
import com.example.expensetracker.components.DividerTextComponent
import com.example.expensetracker.components.HeadingTextComponent
import com.example.expensetracker.components.MyButtonComponent
import com.example.expensetracker.components.MyTextFieldComponent
import com.example.expensetracker.components.NormalTextComponent
import com.example.expensetracker.components.PasswordTextFieldComponent
import com.example.expensetracker.components.UnderlinedNormalTextComponent
import com.example.expensetracker.data.login.LoginUIEvent
import com.example.expensetracker.data.login.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.Navigation.PostOfficeAppRouter
import com.example.expensetracker.Navigation.Screen
import com.example.expensetracker.data.login.onEvent

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
   // val loginUIState = viewModel.loginUIState.value
    val allValidationsPassed =  viewModel.allValidationsPassed.value
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            Spacer(modifier = Modifier.height(40.dp))
            NormalTextComponent(value = stringResource(id = R.string.login))
            HeadingTextComponent(value = stringResource(id = R.string.welcome))

            Spacer(modifier = Modifier.height(30.dp))

            MyTextFieldComponent(labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.email),
                onTextChanged = {
                   viewModel.onEvent(LoginUIEvent.EmailChanged(it))
                },
                errorStatus = viewModel.loginUIState.value.emailError)
            PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password),
                onTextChanged = {
                   viewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(40.dp))
            UnderlinedNormalTextComponent(value = stringResource(id = R.string.forgot))
            Spacer(modifier = Modifier.height(35.dp))

            MyButtonComponent(value = stringResource(id = R.string.login),
                onButtonClicked =
                {
                    viewModel.onEvent(LoginUIEvent.LoginButtonClicked) },
                isEnabled = allValidationsPassed

            )
            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()
            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)

            })


        }
        if(viewModel.loginInProgress.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
    }
}
@Preview
@Composable
fun DefaultPreviewOfLoginScreen() {
    LoginScreen(viewModel = LoginViewModel())
}