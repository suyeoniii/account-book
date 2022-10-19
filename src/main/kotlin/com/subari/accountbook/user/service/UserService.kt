package com.subari.accountbook.user.service

import com.subari.accountbook.user.domain.User
import com.subari.accountbook.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {
    fun findUser(email: String): User {
        return userRepository.findByEmail(email)
    }

    fun existsEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
}