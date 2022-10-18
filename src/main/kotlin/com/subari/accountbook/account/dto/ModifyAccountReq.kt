package com.subari.accountbook.account.dto

import java.time.LocalDate

data class ModifyAccountReq (
                             val amount: Int,
                             val memo: String,
)