package com.subari.accountbook.auth.controller

import com.subari.accountbook.auth.dto.UserLoginReq
import com.subari.accountbook.auth.dto.UserLoginRes
import com.subari.accountbook.auth.dto.UserRegisterReq
import com.subari.accountbook.auth.dto.UserRegisterRes
import com.subari.accountbook.auth.service.AuthService
import com.subari.accountbook.user.domain.User
import com.subari.accountbook.user.service.UserService
import com.subari.accountbook.util.BaseException
import com.subari.accountbook.util.BaseResponse
import com.subari.accountbook.util.BaseResponseCode.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService, private val userService: UserService, private val passwordEncoder: PasswordEncoder) {

    @PostMapping("signup")
    fun register(@RequestBody @Valid userRegisterReq: UserRegisterReq): BaseResponse<UserRegisterRes> {

        if(userService.existsEmail(userRegisterReq.email)) {
            throw BaseException(DUPLICATE_EMAIL)
        }
        userRegisterReq.password = passwordEncoder.encode(userRegisterReq.password)
        val userRegisterRes: UserRegisterRes = authService.createUser(userRegisterReq)

        return BaseResponse(userRegisterRes)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody userLoginReq: UserLoginReq): BaseResponse<UserLoginRes> {
        if(!userService.existsEmail(userLoginReq.email)) {
            throw BaseException(USER_NOT_FOUND)
        }

        val user: User = userService.findUser(userLoginReq.email)

        if(!passwordEncoder.matches(userLoginReq.password, user.password)) {
            throw BaseException(CREDENTIAL_INVALID)
        }

        val userLoginRes = UserLoginRes(authService.login(user.email), user.id!!)

        return BaseResponse(userLoginRes)
    }
}