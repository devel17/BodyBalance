package com.dev.bb.api.controller.system

import com.dev.bb.api.components.KeycloakAdminClient
import com.dev.bb.api.controller.system.MenuController
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MenuController::class, KeycloakAdminClient::class)
@AutoConfigureMockMvc(addFilters = false)
class MenuControllerTest(@Autowired val mockMvc: MockMvc, @Autowired val keycloakAdminClient: KeycloakAdminClient) {


    @Test
    fun getMenuByTypeAndUser() {
//        val token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTSjhhRERJYlVHdDRLR2RDQS1GVWpHS2ZfVVBTalY3RHk2ZkwyWEp1MjBBIn0.eyJleHAiOjE3MzkzMDgzMzEsImlhdCI6MTczOTMwODAzMSwiYXV0aF90aW1lIjoxNzM5MzA1NzA3LCJqdGkiOiIxNTE0OGFhMy0wZjgyLTQwYTUtOGM1NC0xODc3ZjU5YTJjMWYiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxMDMvcmVhbG1zL2JiIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU5MGEwNTAwLWRlODYtNDA1NS1iNjFjLTUzMTZlNjlmYjU2NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImZyb250Iiwic2lkIjoiNzE0YzQzZGQtYTdhZC00ODY2LWIxOTAtYjA1NzgwZTg5ZmZhIiwiYWNyIjoiMCIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJST0xFX0RJUkVDVE9SIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtYmIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBtaXRpbm8gZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6InN1cGVyIERpcmVjdG9yIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic3VwZXJkaXJlY3RvciIsImdpdmVuX25hbWUiOiJzdXBlciIsImZhbWlseV9uYW1lIjoiRGlyZWN0b3IiLCJlbWFpbCI6InN1cGVyZGlyZWN0b3JAYmIucnUifQ.Ad2DX4P9Ww0I_E9QIVLQRsNOZ2nOs24ZPMP2ZdIsnZXPW7mpl_xFxAMfAcYxUZgb8aVFW1Qkp4dowDeQx9ikbIUk-ZFStrAjG1IHHW0p5rGPtjlrA4bv-ByR_HGov6GSyLnjD2RT6mEp0_RQEU3VZaKcQDnSBykEJvokFSzDNKo7yNzntt6Q7YFcfMRJgkM-UCSa47KNJ1__LFbbz0V2RydUvRVGinPChtql-j8JIlSvx0vwyvmJDJy7oI0iW-5VH2FkndEmdmXuWI-wZsHJrfoZgsD8hawvrhqg_hpqToGJhEbXHgllcu6TiCap7NzOZ_FTNdIr2YHJxBpI97WTuA";
        mockMvc.get("/menu/header") {
            contentType = MediaType.APPLICATION_JSON
//            content = "{\"id\":1,\"name\":\"John\"}"
            accept = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
            header("X-User-Name","b45fc977-970f-4cf7-bf19-5137de02502c")
//            headers {
//                setBearerAuth(token)
//            }
        }.andExpect {
            status { MockMvcResultMatchers.status().isOk }
            content {
                contentTypeCompatibleWith("application/json")
                jsonPath("$.size()"){
                    value(3)
                }
            }
        }.andDo {
            print()
        }
    }
}