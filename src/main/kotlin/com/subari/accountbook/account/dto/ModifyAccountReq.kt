package com.subari.accountbook.account.dto

import javax.validation.constraints.Positive

data class ModifyAccountReq (
    @field: Positive(message="2003:금액은 0보다 커야 합니다")
    val amount: Int,
    val memo: String,
)