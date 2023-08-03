package com.wishlist.wishlistapp.Controller

import com.wishlist.wishlistapp.Model.newList
import com.wishlist.wishlistapp.Model.userLoginDetails
import com.wishlist.wishlistapp.Repos.NewListRepository
import com.wishlist.wishlistapp.Repos.userLoginDetailsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal


@Controller
class NewListController {

    @Autowired
    private lateinit var userLoginDetailsRepo: userLoginDetailsRepo

    @Autowired
    private lateinit var newListRepository: NewListRepository

    @GetMapping("/lists")
    fun makeNewList(model: Model): String {
        model.addAttribute("newList", newList()) // Add the newList object to the model
        return "new-list"
    }

    @PostMapping("/new/name")
    fun postNewList(newList: newList, model: Model, principal: Principal): String {
        val username = principal.name
        val userLoginDetails: userLoginDetails? = userLoginDetailsRepo.findByUsername(username)
        val listName = newList.listName
        val description = newList.description
        newList.user= userLoginDetails

        newListRepository.save(newList)
        return "redirect:/index"
    }
}