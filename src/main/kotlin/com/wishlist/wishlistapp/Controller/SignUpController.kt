package com.wishlist.wishlistapp.Controller

import com.wishlist.wishlistapp.Model.WishlistTicket
import com.wishlist.wishlistapp.Model.userLoginDetails
import com.wishlist.wishlistapp.Repos.NewListRepository
import com.wishlist.wishlistapp.Repos.WishlistTicketRepository
import com.wishlist.wishlistapp.Repos.userLoginDetailsRepo
import com.wishlist.wishlistapp.Service.UserPasswordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import javax.annotation.security.PermitAll



@Controller
class SignUpController(private val userLoginDetailsRepo: userLoginDetailsRepo,
                       private val passwordEncoder: PasswordEncoder
) {


    @GetMapping("/sign/up/form")
    @PermitAll
    fun getSignInPage(): String {
        return "signup"
    }



    @PostMapping("/signup")
    @PermitAll
    fun signUpHere(@ModelAttribute userLoginDetails: userLoginDetails,redirectAttributes: RedirectAttributes): String {
        val newUser = userLoginDetails
        newUser.username = userLoginDetails.username
        val passwordService = UserPasswordService()

        val validationOfCap = passwordService.setUsernameCasingRequirements(userLoginDetails.username)
        if (!validationOfCap) {
            redirectAttributes.addFlashAttribute("error", "username must contain at least one capital")
            return "redirect:/sign/up/form"
        }
            val validationOfCapForPassword = passwordService.setPasswordCasingRequirements(userLoginDetails.password)
        if(!validationOfCapForPassword) {
            redirectAttributes.addFlashAttribute("error", "Password must contain at least one capital")
            return "redirect:/sign/up/form"
        }
            if (!passwordService.setPasswordStrength(userLoginDetails.password)) {
                redirectAttributes.addFlashAttribute("error", "Password must contain at least 8 characters")
                return "redirect:/sign/up/form"
            }else{

            val encodedPassword = passwordEncoder.encode(userLoginDetails.password)
            newUser.password = encodedPassword

            userLoginDetailsRepo.save(newUser)
            return "login"
        }
    }

    @GetMapping("/info")
    fun getUserInfo(model: Model): String {
        model.addAttribute("updatedUserDetails", userLoginDetails())

        return "change_info"
    }

    @PostMapping("/modify/info")
    fun changeInfo(
        @ModelAttribute updatedUserDetails: userLoginDetails, principal: Principal, model: Model
    ): String {
        val username = principal.name
        val existingUser: userLoginDetails? = userLoginDetailsRepo.findByUsername(username)


        if (existingUser != null) {

            existingUser.username = updatedUserDetails.username
            existingUser.name = updatedUserDetails.name
            existingUser.password = passwordEncoder.encode(updatedUserDetails.password)

            // Save the updated user details
            userLoginDetailsRepo.save(existingUser)
        }
        return "index"

    }
}