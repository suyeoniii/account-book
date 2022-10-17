package com.subari.accountbook.util

class BaseException(baseResponseCode: BaseResponseCode): RuntimeException() {
    val baseResponseCode: BaseResponseCode = baseResponseCode
}