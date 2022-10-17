package com.subari.accountbook.account.repository

import com.subari.accountbook.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<Account, Long> {
    fun findAccountById(id: Long): Account?
}