package com.subari.accountbook.auth.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class UserLoginReq (
        @field:NotNull
        @field:Email(message="2001:이메일 형식이 잘못되었습니다")
        val email: String,

        @field:Length(min=8, max=20, message="2002:비밀번호 길이는 8-20이어야 합니다")
        var password: String,)