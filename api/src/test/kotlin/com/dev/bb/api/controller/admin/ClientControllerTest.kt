package com.dev.bb.api.controller.admin

import com.dev.bb.api.components.KeycloakAdminClient
import com.dev.bb.api.dto.admin.ClientDto
import com.dev.bb.api.service.ClientService
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.ArgumentMatchers.any
import org.springframework.security.access.AccessDeniedException
import com.dev.bb.api.model.system.Client
import com.dev.bb.api.repository.system.BranchRepository
import com.dev.bb.api.repository.system.ClientRepository

@WebMvcTest(ClientController::class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var keycloakAdminClient: KeycloakAdminClient

    @MockBean
    lateinit var clientService: ClientService

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    
    @MockBean
    private lateinit var clientRepository: ClientRepository

    @MockBean
    private lateinit var branchRepository: BranchRepository

    @Test
    fun `should return all clients`() {
        // given
        val client1 = Client.rand()
        val client2 = Client.rand()
        val userName = "test-user"
        
        `when`(clientService.getAllClients()).thenReturn(listOf(client1, client2))

        // when & then
        mockMvc.get("/admin/client/all") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            header("X-User-Id", userName)
        }.andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.size()") { value(2) }
                jsonPath("$[*].name") { exists() }
                jsonPath("$[*].email") { exists() }
            }
        }.andDo {
            print()
        }
    }

    @Test
    fun `should create new client when valid data`() {
        // given
        val createDto = ClientDto(
            name = "Test Client",
            email = "test@example.com",
            phone = "+1234567890",
            branchId = 1L
        )
        val userName = "test-user"
        
        `when`(clientService.createClient(createDto)).thenReturn(1L)

        // when & then
        mockMvc.post("/admin/client") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(createDto)
            header("X-User-Id", userName)
        }.andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.id") { value(1) }
                jsonPath("$.name") { value(createDto.name) }
                jsonPath("$.email") { value(createDto.email) }
                jsonPath("$.phone") { value(createDto.phone) }
            }
        }.andDo {
            print()
        }
    }
    
    @Test
    fun `should return 403 when user has no permission to create client`() {
        // given
        val createDto = ClientDto(
            name = "Test Client",
            email = "test@example.com",
            phone = "+1234567890",
            branchId = 1L
        )
        val userName = "test-user"
        
        // when & then
        mockMvc.post("/admin/client") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(createDto)
            header("X-User-Id", userName)
        }.andExpect {
            status { isForbidden() }
        }.andDo {
            print()
        }
    }
    
    @Test
    fun `should return 400 when invalid data`() {
        // given
        val createDto = ClientDto(
            name = "",  // invalid: blank name
            email = "invalid-email",  // invalid email format
            phone = "123",  // invalid phone format
            branchId = 1L
        )
        val userName = "test-user"
        
        // when & then
        mockMvc.post("/admin/client") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(createDto)
            header("X-User-Id", userName)
        }.andExpect {
            status { isBadRequest() }
        }.andDo {
            print()
        }
    }
}

