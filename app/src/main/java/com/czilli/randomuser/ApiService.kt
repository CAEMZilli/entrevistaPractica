package com.czilli.randomuser

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api")
    suspend fun getRandomUser(): Response<UserResponse>
}