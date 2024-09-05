package com.example.appy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform