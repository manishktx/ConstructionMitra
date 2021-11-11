package com.constructionmitra.user

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TestCoroutines {

    fun main(){
        GlobalScope.launch {

            println("Starting coroutine thread:${Thread.currentThread()}")
            delay(1000)
        }
        runBlocking {
            delay(1000)
        }

    }
}