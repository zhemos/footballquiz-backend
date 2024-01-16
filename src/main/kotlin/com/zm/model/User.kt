package com.zm.model

import com.google.gson.annotations.SerializedName
import io.ktor.server.auth.*

data class User(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("role")
    val role: String
)