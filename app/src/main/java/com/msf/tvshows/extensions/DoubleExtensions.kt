package com.msf.tvshows.extensions

fun Double.divideHalf() = runCatching { this / 2 }.onFailure { 0.0 }.getOrElse { 0.0 }
