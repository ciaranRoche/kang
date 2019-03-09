package org.ciaranroche.kang

fun wait_splash() {
    try {
        Thread.sleep(2000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
}

fun getRandomString(length: Int): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}