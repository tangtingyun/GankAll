package com.step.gankall

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HelloWorldTest {
    @Test
    fun addition_isCorrect() {
    }


    @Test
    fun runBlockTest() {
        runBlock {
            List(1000) {
                println("HelloWorld")
            }
        }
    }

    fun runBlock(block: () -> Unit) {
        val start = System.currentTimeMillis()
        block()
        println(System.currentTimeMillis() - start)
    }
}
