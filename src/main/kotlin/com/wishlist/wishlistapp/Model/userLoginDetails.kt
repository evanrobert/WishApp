package com.wishlist.wishlistapp.Model

import lombok.Data
import javax.persistence.*

@Entity
@Data
class userLoginDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var username: String = ""
    var password: String = ""
    var name: String = ""
    @OneToMany(mappedBy = "user")
    var wish: List<WishlistTicket>? = null

    @OneToMany(mappedBy = "user")
    var list: List<newList>? = null

}