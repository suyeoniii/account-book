package com.subari.accountbook.auth.service

import com.subari.accountbook.auth.dto.UserLoginReq
import com.subari.accountbook.auth.dto.UserLoginRes
import com.subari.accountbook.auth.dto.UserRegisterReq
import com.subari.accountbook.auth.dto.UserRegisterRes
import com.subari.accountbook.security.jwt.JwtTokenProvider
import com.subari.accountbook.user.domain.User
import com.subari.accountbook.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.security.SecureRandom

@Service
class AuthService(private val userRepository: UserRepository, private val jwtTokenProvider: JwtTokenProvider) {

    fun createUser(userRegisterReq: UserRegisterReq): UserRegisterRes {
        val user = User(userRegisterReq.email, userRegisterReq.password)
        val createdUser = userRepository.save(user)
        val token = jwtTokenProvider.createToken(createdUser.email)

        return UserRegisterRes(token, createdUser.id!!)
    }

    fun login(email: String): String {
        val token: String = jwtTokenProvider.createToken(email)

        return token
    }
}
