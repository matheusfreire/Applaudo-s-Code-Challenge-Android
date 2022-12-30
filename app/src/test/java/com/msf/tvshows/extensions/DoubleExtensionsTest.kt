package com.msf.tvshows.extensions

import io.kotlintest.shouldBe
import org.junit.Test

internal class DoubleExtensionsTest {

    @Test
    fun `GIVEN value is gt zero WHEN call divideHalf THEN should return half of this value`() {
        val ten = 10.0

        ten.divideHalf() shouldBe 5.0
    }

    @Test
    fun `GIVEN value is lt zero WHEN call divideHalf THEN should return half of this value`() {
        val ten = -10.0

        ten.divideHalf() shouldBe -5.0
    }

    @Test
    fun `GIVEN value is eq zero WHEN call divideHalf THEN should return zero`() {
        val zero = 0.0

        zero.divideHalf() shouldBe 0.0
    }
}