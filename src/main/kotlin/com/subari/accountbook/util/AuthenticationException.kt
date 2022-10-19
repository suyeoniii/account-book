package com.subari.accountbook.util

import org.springframework.security.core.AuthenticationException

class AuthenticateException(message: String) : AuthenticationException(message)