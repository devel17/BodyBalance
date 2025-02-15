package com.dev.bb.api.components

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Ignore

@SpringBootTest
@ActiveProfiles("test")
@Ignore
class KeycloakAdminClientTest(@Autowired val keycloakAdminClient: KeycloakAdminClient) {

    @Test
    fun getUser() {
        keycloakAdminClient.getUser("9b1f22d9-1177-4b8c-b053-948df9ef8757").also { println(it.toRepresentation().username) }
    }

    @Test
    fun getSuperDirectorGroups() = keycloakAdminClient.getUserGroups("9b1f22d9-1177-4b8c-b053-948df9ef8757").forEach {println(it.name)}

    @Test
    fun getDirectorGroups() {
        val group = keycloakAdminClient.getUserGroups("9b1f22d9-1177-4b8c-b053-948df9ef8757").first()
        println(group.name)
        println(group.attributes?.size)
        println(group.id)
        println(group.access)
        println(group.subGroups)
        println(group.attributes?.get("description"))
    }

    @Test
    fun getDirector2Groups() = keycloakAdminClient.getUserGroups("b45fc977-970f-4cf7-bf19-5137de02502c").forEach {println("${it.name} : ${it.attributes?.keys}" )}

    @Test
    fun getGroups() {
        keycloakAdminClient.getGroups().groups().forEach {println("${it.name} : ${it.attributes?.keys}" )}
    }

    @Test
    fun getRoles() {
        keycloakAdminClient.getRoles().list().forEach { println(it) }
    }
}