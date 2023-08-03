package com.wishlist.wishlistapp.Model

import lombok.*
import javax.persistence.*

@Data
@Entity
class newList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var listName: String = ""
    var description: String = ""

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: userLoginDetails? = null



    @OneToMany(mappedBy = "list")
    var wish: List<WishlistTicket>? = null
}