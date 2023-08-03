package com.wishlist.wishlistapp.Repos

import com.wishlist.wishlistapp.Model.WishlistTicket
import com.wishlist.wishlistapp.Model.userLoginDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WishlistTicketRepository : JpaRepository<WishlistTicket, Long> {
    fun findByUser(user: userLoginDetails?): List<WishlistTicket?>
}