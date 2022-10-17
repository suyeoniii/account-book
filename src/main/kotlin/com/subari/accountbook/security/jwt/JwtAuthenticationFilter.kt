package com.subari.accountbook.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authorizationHeader: String? = request.getHeader("Authorization") ?: return filterChain.doFilter(request, response)
        val token = authorizationHeader?.substring("Bearer ".length) ?: return filterChain.doFilter(request, response)

        if (jwtTokenProvider.validation(token)) {

            val email = jwtTokenProvider.parseEmail(token)
            val authentication: Authentication = jwtTokenProvider.getAuthentication(email)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}
