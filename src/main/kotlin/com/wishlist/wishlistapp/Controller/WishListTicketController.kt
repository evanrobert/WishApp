package com.wishlist.wishlistapp.Controller


import com.wishlist.wishlistapp.Model.WishlistTicket
import com.wishlist.wishlistapp.Model.userLoginDetails
import com.wishlist.wishlistapp.Repos.NewListRepository
import com.wishlist.wishlistapp.Repos.WishlistTicketRepository
import com.wishlist.wishlistapp.Repos.userLoginDetailsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

import java.security.Principal

@Controller
class WishListTicketController {

    @Autowired
    private lateinit var wishlistTicketRepository: WishlistTicketRepository

    @Autowired
    private lateinit var userLoginDetailsRepo: userLoginDetailsRepo

    @Autowired
    private lateinit var newListRepository: NewListRepository


    @GetMapping("/Ticket")
    fun submitTicketForm(model: Model, principal: Principal): Any {
        val username = principal.name
        val userLoginDetails: userLoginDetails = userLoginDetailsRepo.findByUsername(username)
        val userLists = newListRepository.findByUser(userLoginDetails)

        if (userLists.isEmpty()) {
            return RedirectView("/lists")
        }

        model.addAttribute("userLists", userLists)
        model.addAttribute("ticket", WishlistTicket())
        return "submitTicket"
    }


    @PostMapping("/submit/Ticket")
    fun submitTicket(
        @ModelAttribute ticket: WishlistTicket, @RequestParam("list.id") listId: Long?, model: Model,
        principal: Principal
    ): String {
        val username = principal.name
        val userLoginDetails: userLoginDetails = userLoginDetailsRepo.findByUsername(username)
        val maxDescriptionLength = 255
        val description = ticket.description.take(maxDescriptionLength)
        val rating = ticket.rating
        if (rating != null) {
            if ((rating < 1) || (rating > 5)) {
                model.addAttribute("errorMessage", "Rating must be between 1-5")
                return "submitTicket"
            }
        }

        ticket.user = userLoginDetails

        val selectedList = listId?.let { newListRepository.findById(it).orElse(null) }


        ticket.list = selectedList

        wishlistTicketRepository.save(ticket)

        return "redirect:/index"
    }

    @GetMapping("/results")
    fun getResults(
        @RequestParam(name = "listId", required = false)
        listId: Long?, model: Model, principal: Principal
    ):
            String {
        val username = principal.name
        val userLoginDetails: userLoginDetails = userLoginDetailsRepo.findByUsername(username)


        val userLists = newListRepository.findByUser(userLoginDetails)
        model.addAttribute("userName", userLoginDetails.name)
        model.addAttribute("userLists", userLists)

        if (listId != null) {
            val selectedList = newListRepository.findById(listId).orElse(null)

            if (selectedList != null) {

                val tickets = selectedList.wish
                model.addAttribute("tickets", tickets)


            }
        }

        return "Wishlist"
    }


    @PostMapping("/results")
    fun showSelectedList(@RequestParam("listId") listId: Long?, model: Model, principal: Principal): String {
        val username = principal.name
        val userLoginDetails: userLoginDetails = userLoginDetailsRepo.findByUsername(username)

        val userLists = newListRepository.findByUser(userLoginDetails)
        model.addAttribute("userLists", userLists)
        val selectedList = listId?.let { newListRepository.findById(it).orElse(null) }

        if (selectedList != null) {
            val tickets = selectedList.wish
            model.addAttribute("tickets", tickets)
        } else {
            model.addAttribute("tickets", null)
        }
        return "Wishlist"
    }

    @PostMapping("/delete/ticket")
    fun deleteTicket(
        @RequestParam("selectedTickets") ticketIds: List<Long>?,
        redirectAttributes: RedirectAttributes
    ): String {
        if (ticketIds.isNullOrEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select at least one ticket to delete.")
        } else {
            for (ticketId in ticketIds) {
                wishlistTicketRepository.deleteById(ticketId)
            }
        }
        return "redirect:/results"
    }

    @GetMapping("/modify/ticket/{id}")
    fun showModifyForm(@PathVariable id: Long, model: Model, principal: Principal): String {
        val username = principal.name
        val userLoginDetails: userLoginDetails = userLoginDetailsRepo.findByUsername(username)
        val wishlistTicket = wishlistTicketRepository.findById(id)
        if (wishlistTicket.isPresent) {
            model.addAttribute("wishlistTicket", wishlistTicket.get())
            return "modify_ticket"
        }
        return "index"
    }


    @PatchMapping("/modify/ticket/{id}")
    fun modifyTicket(
        @PathVariable id: Long,
        @ModelAttribute modifiedTicket: WishlistTicket,
        model: Model,
        principal: Principal
    ): String {
        val username = principal.name
        val userLoginDetails: userLoginDetails = userLoginDetailsRepo.findByUsername(username)


        val existingTicket = wishlistTicketRepository.findById(id).orElse(null)

        existingTicket?.let {

            it.apply {
                description = modifiedTicket.description
                rating = modifiedTicket.rating
                Url = modifiedTicket.Url
                imageUrl = modifiedTicket.imageUrl
                name = modifiedTicket.name
                user = userLoginDetails
            }
            wishlistTicketRepository.save(existingTicket)
        }

        return "Wishlist"
    }


}