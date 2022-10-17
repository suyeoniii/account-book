package com.subari.accountbook.auth.dto

data class UserLoginRes (
    val token: String,
    val userId: Long,
)