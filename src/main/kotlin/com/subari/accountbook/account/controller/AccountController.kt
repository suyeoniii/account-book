package com.subari.accountbook.account.controller

import com.subari.accountbook.account.domain.AccountStatus
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
    fun createAccount(authentication: Authentication, @RequestBody @Valid postAccountReq: PostAccountReq): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username) // find by email

        val accountId = accountService.createAccount(postAccountReq, user)
        val postAccountRes = PostAccountRes(accountId)

        return BaseResponse(postAccountRes)
    }

    @PutMapping("/{accountId}")
    fun modifyAccount(authentication: Authentication, @PathVariable accountId: String, @RequestBody @Valid modifyAccountReq: ModifyAccountReq): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        accountService.modifyAccount(accountId.toLong(), user, modifyAccountReq.amount, modifyAccountReq.memo, modifyAccountReq.date)

        return BaseResponse(SUCCESS)
    }

    @PatchMapping("/{accountId}/deletion")
    fun deleteAccount(authentication: Authentication, @PathVariable accountId: String): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        accountService.updateAccountStatus(accountId.toLong(), user, AccountStatus.DELETED)

        return BaseResponse(SUCCESS)
    }

    @PatchMapping("/{accountId}/restoration")
    fun restoreAccount(authentication: Authentication, @PathVariable accountId: String): BaseResponse<Any> {

        val userDetails: UserDetails = authentication.principal as UserDetails
        val user = userService.findUser(userDetails.username)

        accountService.updateAccountStatus(accountId.toLong(), user, AccountStatus.ACTIVE)

        return BaseResponse(SUCCESS)
    }

}