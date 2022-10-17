package com.subari.accountbook.account.dto

import java.time.LocalDate

data class PostAccountReq (
    // TODO: 양수만 가능하게
    val amount: Int,
    val memo: String,
    val date: LocalDate
        )