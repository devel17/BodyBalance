package com.dev.bb.api.model

import com.dev.bb.api.enum.FoodType
import com.dev.bb.model.Domain
import jakarta.persistence.Entity

@Entity
class Menu(
    var name: String,
    var description: String,
    var root: Boolean
) : Domain(){
}