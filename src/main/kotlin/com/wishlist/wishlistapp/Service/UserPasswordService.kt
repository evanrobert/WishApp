package com.wishlist.wishlistapp.Service


import com.wishlist.wishlistapp.Model.userLoginDetails
import org.springframework.beans.factory.annotation.Autowired

class UserPasswordService {
    @Autowired
    private lateinit var userLoginDetails: userLoginDetails

    fun setUsernameCasingRequirements(username: String): Boolean {
        val uppercaseLetterRegex = Regex(".*[A-Z].*")

        if (!uppercaseLetterRegex.matches(username)) {
            return false
        }
        return true
    }

    fun setPasswordCasingRequirements(password: String): Boolean {
        val uppercaseLetterRegex = Regex(".*[A-Z].*")

        if (!uppercaseLetterRegex.matches(password)) {
            return false
        }
        return true

    }

    fun setPasswordStrength(password: String): Boolean {
        if (password.length < 8) {
            return false
        }
        return true

    }

    fun setUserNameReqs(username: String): Boolean {
        if (username.length < 6) {
            return false
        }
        return true
    }
}