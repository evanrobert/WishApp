package com.wishlist.wishlistapp.Model

import lombok.*
import javax.persistence.*



@Entity
@Data
@Builder
class WishlistTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var Url: String = ""
    var imageUrl: String = ""
    var name: String = ""
    var price: Double? = null
    var rating: Int? = null
    var description: String= ""
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: userLoginDetails? = null
    @ManyToOne
    @JoinColumn(name = "list_id")
    var list: newList? = null


}