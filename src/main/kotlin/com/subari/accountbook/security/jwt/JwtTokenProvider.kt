package com.subari.accountbook.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {
    private var secretKey = "testeststsetse"

    private val tokenValidTime = 10000000

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(email: String): String {
        val claims: Claims = Jwts.claims().setSubject("email")
        claims["email"] = email
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun validation(token: String) : Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseEmail(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["email"] as String
    }

    fun getAuthentication(email: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails, "", mutableListOf())
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
    }
}