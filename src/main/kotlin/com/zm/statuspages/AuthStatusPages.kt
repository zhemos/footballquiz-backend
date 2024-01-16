package com.zm.statuspages

data class AuthenticationException(override val message: String = "Authentication failed") : Exception()