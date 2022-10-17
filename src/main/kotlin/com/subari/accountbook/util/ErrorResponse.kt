package com.subari.accountbook.util

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    @field:JsonProperty("isSuccess")
    var isSuccess: Boolean? = false,

    @field:JsonProperty("code")
    var code: Int? = null,

    @field:JsonProperty("message")
    var message: String? = null,
)