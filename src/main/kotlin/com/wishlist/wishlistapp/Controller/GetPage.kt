package com.wishlist.wishlistapp.Controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class getPage {

    @GetMapping("/index")
    fun getIndex() : String {
        return "index";
    }
    @GetMapping("/about")
    fun getAbout(): String {
        return "about"
    }

    @GetMapping("/login")
    fun showLoginPage(): String{
        return "login"
    }
    @GetMapping("/contact")
    fun getContactCenter(): String{
        return "contact"
    }

}