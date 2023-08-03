package com.wishlist.wishlistapp.Service


import com.wishlist.wishlistapp.Model.userLoginDetails
import org.springframework.beans.factory.annotation.Autowired

class UserPasswordService {
    @Autowired
    private lateinit var userLoginDetails: userLoginDetails

    fun setUsernameRequirements(username: String): Boolean {
        val uppercaseLetterRegex = Regex(".*[A-Z].*")

        if (!uppercaseLetterRegex.matches(username)) {
            return false
        }
        return true
    }
}