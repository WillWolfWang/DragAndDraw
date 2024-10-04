package com.will.draganddraw

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        num1AndNum2(1, 2, ::plus)// 使用::引用函数

        num1AndNum2(1, 2) { n1, n2 ->
            n1 + n2
        }
    }

    fun num1AndNum2(num1: Int, num2: Int, operation:(Int, Int) -> Int): Int{
        val result = operation(num1, num2)
        println(result)
        return result
    }

    fun plus(num1: Int, num2: Int): Int {
        return num1 + num2
    }
}