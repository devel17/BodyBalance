package com.dev.bb.api.controller.common

import com.dev.bb.model.Profile
import com.dev.bb.api.repo.ProfileRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.Ignore

@WebMvcTest(ProfileController::class)
@Ignore
class ProfileControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var profileRepository: ProfileRepository

    @Test
    fun `test get all profiles`() {
        // given

        val profiles = listOf(Profile(), Profile())
        `when`(profileRepository.findAll()).thenReturn(profiles)

        // when
        mockMvc.get("/api/profile") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
        }.andExpect {
            status { MockMvcResultMatchers.status().isOk }
            content {
                contentTypeCompatibleWith("application/json")
                jsonPath("$.size()") { value(2) }
            }
        }
    }

    @Test
    @Ignore
    fun `test create profile`() {
        // given
        val profile = Profile()
        `when`(profileRepository.save(profile)).thenReturn(profile)

        // when
        mockMvc.post("/api/profile") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\"id\":1,\"name\":\"John\"}"
            accept = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
        }.andExpect {
            status { MockMvcResultMatchers.status().isOk }
            content {
                contentTypeCompatibleWith("application/json")
                jsonPath("$.id") { value(1) }
                jsonPath("$.name") { value("John") }
            }
        }
    }
}