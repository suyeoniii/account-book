package com.subari.accountbook.security.jwt

import com.subari.accountbook.util.AuthenticateException
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {
    private var secretKey = "jwt_secret_key"
    private val tokenValidTime = 60 * 60 * 24 * 30

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
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
        } catch (expiredJwtException: ExpiredJwtException) {
            throw AuthenticateException("Jwt 토큰이 만료되었습니다.")
        } catch (unsupportedJwtException: UnsupportedJwtException) {
            throw AuthenticateException("Jwt 토큰이 만료되었습니다.")
        } catch (malformedJwtException: MalformedJwtException) {
            throw AuthenticateException("Jwt 토큰이 만료되었습니다.")
        } catch (signatureException: SignatureException) {
            throw AuthenticateException("Jwt 토큰이 만료되었습니다.")
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw AuthenticateException("Jwt 토큰이 만료되었습니다.")
        }
    }
}