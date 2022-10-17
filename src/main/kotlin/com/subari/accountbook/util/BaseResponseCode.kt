package com.subari.accountbook.util

import org.springframework.http.HttpStatus

enum class BaseResponseCode(isSuccess: Boolean, code: Int, status: HttpStatus, message: String) {
    SUCCESS(true, 1000, HttpStatus.OK,"성공"),

    DUPLICATE_EMAIL(false, 3001, HttpStatus.CONFLICT, "중복된 이메일입니다"),
    USER_NOT_FOUND(false, 3004, HttpStatus.NOT_FOUND, "이메일, 비밀번호가 일치하지 않습니다"),
    CREDENTIAL_INVALID(false, 3005, HttpStatus.BAD_REQUEST, "이메일, 비밀번호가 일치하지 않습니다"),
    ACCOUNT_NOT_FOUND(false, 3006, HttpStatus.BAD_REQUEST, "존재하지 않는 가계부 내역입니다"),
    ACCOUNT_USER_NOT_MATCH(false, 3006, HttpStatus.BAD_REQUEST, "사용자 정보와 일치하지 않습니다")
    ;

    val isSuccess: Boolean = isSuccess
    val code: Int = code
    val status: HttpStatus = status
    val message: String = message
}