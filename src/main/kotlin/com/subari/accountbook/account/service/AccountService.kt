package com.subari.accountbook.account.service

import com.subari.accountbook.account.domain.Account
import com.subari.accountbook.account.domain.AccountStatus
import com.subari.accountbook.account.repository.AccountRepository
import com.subari.accountbook.account.dto.PostAccountReq
import com.subari.accountbook.user.domain.User
import com.subari.accountbook.util.BaseException
import com.subari.accountbook.util.BaseResponseCode.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AccountService(private val accountRepository: AccountRepository) {

    @Transactional
    fun createAccount(postAccountReq: PostAccountReq, user: User): Long {
        val account = Account(postAccountReq.amount, postAccountReq.memo, AccountStatus.ACTIVE, user)
        val insertedAccount = accountRepository.save(account)
        return insertedAccount.id!!
    }

    @Transactional
    fun modifyAccount(accountId: Long, user: User, amount: Int, memo: String) {
        val account = accountRepository.findAccountById(accountId)

        if(account === null) {
            throw BaseException(ACCOUNT_NOT_FOUND)
        }

        if(account.user !== user){
            throw BaseException(ACCOUNT_USER_NOT_MATCH)
        }

        account.amount = amount
        account.memo = memo

        accountRepository.save(account)
    }

    @Transactional
    fun updateAccountStatus(accountId: Long, user: User, status: AccountStatus){
        val account = accountRepository.findAccountById(accountId)

        if(account === null) {
            throw BaseException(ACCOUNT_NOT_FOUND)
        }

        if(account.user !== user){
            throw BaseException(ACCOUNT_USER_NOT_MATCH)
        }

        if(status === AccountStatus.DELETED && account.status === AccountStatus.DELETED){
            throw BaseException(ACCOUNT_STATUS_ALREADY_DELETED)
        }

        if(status === AccountStatus.ACTIVE && account.status === AccountStatus.ACTIVE){
            throw BaseException(ACCOUNT_STATUS_ALREADY_ACTIVE)
        }

        account.status = status
        accountRepository.save(account)
    }

    fun getAccountList(user: User): List<Account> {
        val accounts = accountRepository.findAllByUser(user)

        return accounts
    }

    fun getAccount(accountId: Long, user: User): Account {
        val account = accountRepository.findAccountById(accountId)

        if(account === null) {
            throw BaseException(ACCOUNT_NOT_FOUND)
        }

        if(account.user !== user){
            throw BaseException(ACCOUNT_USER_NOT_MATCH)
        }

        return account
    }
}