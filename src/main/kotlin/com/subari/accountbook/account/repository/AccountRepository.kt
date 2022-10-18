package com.subari.accountbook.account.repository

import com.subari.accountbook.account.domain.Account
import com.subari.accountbook.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<Account, Long> {
    fun findAccountById(id: Long): Account?
    fun findAllByUser(user: User): List<Account>
}