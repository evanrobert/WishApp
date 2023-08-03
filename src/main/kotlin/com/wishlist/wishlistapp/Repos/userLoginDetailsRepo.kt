package com.wishlist.wishlistapp.Repos


import com.wishlist.wishlistapp.Model.userLoginDetails

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface userLoginDetailsRepo: JpaRepository<userLoginDetails, Long>{
    fun findByUsername(username: String): userLoginDetails


}