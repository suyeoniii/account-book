package com.subari.accountbook.user.repository

import com.subari.accountbook.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User
    fun existsByEmail(email: String): Boolean
}