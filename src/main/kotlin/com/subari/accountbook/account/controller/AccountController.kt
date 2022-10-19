package com.subari.accountbook.account.controller

import com.subari.accountbook.account.domain.AccountStatus
import com.subari.accountbook.account.dto.GetAccountRes
import com.subari.accountbook.account.dto.ModifyAccountReq
import com.subari.accountbook.account.service.AccountService
import com.subari.accountbook.account.dto.PostAccountReq
import com.subari.accountbook.account.dto.PostAccountRes
import com.subari.accountbook.user.service.UserService
import com.subari.accountbook.util.BaseResponse
import com.subari.accountbook.util.BaseResponseCode.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/accounts")
class AccountController(private val accountService: AccountService, private val userService: UserService) {

    @PostMapping("")
    fun createAccount(authentication: Authentication, @Valid @RequestBody postAccountReq: PostAccountReq): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username) // find by email

        val accountId = accountService.createAccount(postAccountReq, user)
        val postAccountRes = PostAccountRes(accountId)

        return BaseResponse(postAccountRes)
    }

    @GetMapping("")
    fun getAccountList(authentication: Authentication): BaseResponse<List<GetAccountRes>> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val accounts = accountService.getAccountList(user)

        val getAccountListRes: ArrayList<GetAccountRes> = arrayListOf()
        accounts.forEach{
            getAccountListRes.add(GetAccountRes(it.id!!, it.amount, it.memo, it.status, it.createdAt!!))
        }

        return BaseResponse(getAccountListRes)
    }

    @GetMapping("/{accountId}")
    fun getAccount(authentication: Authentication, @PathVariable accountId: Long): BaseResponse<GetAccountRes> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        val account = accountService.getAccount(accountId, user)
        val getAccountRes = GetAccountRes(account.id!!, account.amount, account.memo, account.status, account.createdAt!!)

        return BaseResponse(getAccountRes)
    }


    @PutMapping("/{accountId}")
    fun modifyAccount(authentication: Authentication, @PathVariable accountId: Long, @Valid @RequestBody modifyAccountReq: ModifyAccountReq): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        accountService.modifyAccount(accountId, user, modifyAccountReq.amount as Int, modifyAccountReq.memo)

        return BaseResponse(SUCCESS)
    }

    @PatchMapping("/{accountId}/deletion")
    fun deleteAccount(authentication: Authentication, @PathVariable accountId: Long): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        accountService.updateAccountStatus(accountId, user, AccountStatus.DELETED)

        return BaseResponse(SUCCESS)
    }

    @PatchMapping("/{accountId}/restoration")
    fun restoreAccount(authentication: Authentication, @PathVariable accountId: Long): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        accountService.updateAccountStatus(accountId, user, AccountStatus.ACTIVE)

        return BaseResponse(SUCCESS)
    }

}