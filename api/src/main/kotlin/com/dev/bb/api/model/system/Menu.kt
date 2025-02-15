package com.dev.bb.api.model.system

import com.dev.bb.api.enum.FoodType
import com.dev.bb.model.Domain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "menus")
data class Menu(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var root: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
    var parentMenu: Menu? = null,

    @OneToMany(mappedBy = "parentMenu", cascade = [CascadeType.ALL], orphanRemoval = true)
    var subMenus: MutableList<Menu> = mutableListOf(),

) : Domain() {
    fun addSubMenu(subMenu: Menu) {
        subMenus.add(subMenu)
        subMenu.parentMenu = this
    }

    fun removeSubMenu(subMenu: Menu) {
        subMenus.remove(subMenu)
        subMenu.parentMenu = null
    }
}