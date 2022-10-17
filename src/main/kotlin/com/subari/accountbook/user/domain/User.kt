package com.subari.accountbook.user.domain

import com.subari.accountbook.account.domain.Account
import com.subari.accountbook.util.BaseTime
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
class User(email: String, password: String): BaseTime(), UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null

    @Column(nullable = false, unique = true)
    var email: String = email

    @Column(nullable = false)
    private var password: String = password

    @OneToMany(mappedBy = "user")
    var accounts: List<Account> = ArrayList<Account>()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}