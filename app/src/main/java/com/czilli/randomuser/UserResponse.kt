package com.czilli.randomuser

data class UserResponse(val results: List<User>)

data class User(
    val name: Name,
    val email: String,
    val dob: Dob,
    val location: Location,
    val phone: String,
    val login: Login,
    val picture: Picture
)

data class Name(val title: String, val first: String, val last: String)
data class Dob(val date: String)
data class Location(val street: Street, val city: String, val state: String, val country: String, val postcode: String)
data class Street(val number: Int, val name: String)
data class Login(val password: String)
data class Picture(val large: String)
