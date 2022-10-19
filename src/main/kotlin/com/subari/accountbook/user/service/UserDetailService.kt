package com.subari.accountbook.user.service

import com.subari.accountbook.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserDetailService(private val userRepository: UserRepository): UserDetailsService{
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email)
    }
}