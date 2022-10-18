package com.subari.accountbook.account.service

import com.subari.accountbook.account.domain.Account
import com.subari.accountbook.account.domain.AccountStatus
import com.subari.accountbook.account.repository.AccountRepository
import com.subari.accountbook.account.dto.PostAccountReq
import com.subari.accountbook.user.domain.User
import com.subari.accountbook.util.BaseException
import com.subari.accountbook.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AccountService(private val accountRepository: AccountRepository) {

    fun createAccount(postAccountReq: PostAccountReq, user: User): Long {
        val account = Account(postAccountReq.amount, postAccountReq.memo, postAccountReq.date, AccountStatus.ACTIVE, user)
        val insertedAccount = accountRepository.save(account)
        return insertedAccount.id!!
    }

    fun modifyAccount(accountId: Long, user: User, amount: Int, memo: String, date: LocalDate) {
        val account = accountRepository.findAccountById(accountId)

        if(account === null) {
            throw BaseException(ACCOUNT_NOT_FOUND)
        }

        if(account.user !== user){
            throw BaseException(ACCOUNT_USER_NOT_MATCH)
        }

        account.amount = amount
        account.memo = memo
        account.date = date

        accountRepository.save(account)
    }

    fun updateAccountStatus(accountId: Long, user: User, status: AccountStatus){
        val account = accountRepository.findAccountById(accountId)

        if(account === null) {
            throw BaseException(ACCOUNT_NOT_FOUND)
        }

        if(account.user !== user){
            throw BaseException(ACCOUNT_USER_NOT_MATCH)
        }

        account.status = status
        accountRepository.save(account)
    }
}