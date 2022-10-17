package com.subari.accountbook.user.service

import com.subari.accountbook.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val userRepository: UserRepository): UserDetailsService{
    override fun loadUserByUsername(email: String): UserDetails {
        println(userRepository.findByEmail(email))
        return userRepository.findByEmail(email)
    }
}