package com.example.expensetracker.data

data class RegistrationUIState(
    val firstName :String = "",
    val lastName :String = "",
    val email :String = "",
    val password :String = "",
    val privacyPolicyAccepted :Boolean = false,


    var firstNameError :Boolean = false,
    var lastNameError :Boolean = false,
    var emailError :Boolean = false,
    var passwordError :Boolean = false,
    var privacyPolicyError :Boolean = false,

    )