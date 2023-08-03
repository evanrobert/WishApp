package com.wishlist.wishlistapp.Security


import com.wishlist.wishlistapp.Repos.userLoginDetailsRepo
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: userLoginDetailsRepo) : UserDetailsService {

    private val passwordEncoder: PasswordEncoder

    init {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        return User(user.username, user.password, emptyList()) // Use the stored encoded password directly
    }

}