package com.subari.accountbook.account.dto

import com.subari.accountbook.account.domain.Account
import com.subari.accountbook.account.domain.AccountStatus
import java.time.LocalDateTime

data class GetAccountRes (
    val id: Long,
    val amount: Int,
    val memo: String,
    val status: AccountStatus,
    val createdAt: LocalDateTime
        )