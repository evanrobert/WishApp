package com.wishlist.wishlistapp.Repos


import com.wishlist.wishlistapp.Model.newList
import com.wishlist.wishlistapp.Model.userLoginDetails
import org.springframework.data.jpa.repository.JpaRepository

interface NewListRepository: JpaRepository<newList,Long> {
    fun findByUser(user2: userLoginDetails?): List<newList?>

}