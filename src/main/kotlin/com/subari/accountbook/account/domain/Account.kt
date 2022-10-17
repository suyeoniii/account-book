package com.subari.accountbook.account.domain

import com.subari.accountbook.user.domain.User
import com.subari.accountbook.util.BaseTime
import java.time.LocalDate
import javax.persistence.*

@Entity
class Account(amount: Int, memo: String, date: LocalDate, user: User): BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    var id: Long? = null

    @Column(nullable = false)
    var amount: Int = amount

    @Column(nullable = false)
    var memo: String = memo

    @Column(nullable = false)
    var date: LocalDate = date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = user
}