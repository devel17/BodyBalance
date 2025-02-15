package com.dev.bb.api.components

import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.resource.GroupsResource
import org.keycloak.admin.client.resource.RolesResource
import org.keycloak.admin.client.resource.UserResource
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class KeycloakAdminClient(
    @param:Value("\${keycloak.realm}") private val realmName: String,

    @param:Value("\${keycloak.url}") private val url: String,

    @param:Value("\${keycloak.username}") private val adminUsername: String,

    @param:Value("\${keycloak.password}") private val adminPassword: String
) {
    val keycloak = Keycloak.getInstance(
        this.url,
        "master",
        adminUsername,
        adminUsername,
        "admin-cli"
    )
    fun getUser(userId: String?) : UserResource {
        val user = keycloak.realm(realmName).users().get(userId)
        return user
    }

    fun getGroups() : GroupsResource = keycloak.realm(realmName).groups()

    fun getRoles() :RolesResource = keycloak.realm(realmName).roles()

    /**
     * Получает все группы, к которым принадлежит пользователь.
     *
     * @param userId ID пользователя.
     * @return Список групп (GroupRepresentation).
     */
    fun getUserGroups(userId: String): List<GroupRepresentation> = keycloak.realm(realmName).users().get(userId).groups()

    /**
     * Получает ID пользователя по его имени.
     *
     * @param username Имя пользователя.
     * @return ID пользователя.
     *   "sub": "590a0500-de86-4055-b61c-5316e69fb567",
     *   "typ": "ID",
     *   "azp": "front",
     *   "sid": "818aeca4-fbc3-42ee-9cc7-11715476c891",
     */
    fun getUserIdByUsername(username: String): String? {
        val realmResource = keycloak.realm(realmName)
        val usersResource = realmResource.users()

        // Поиск пользователя по имени
        val users = usersResource.search(username)
        return users.firstOrNull()?.id
    }

//    val timeBlockedSeconds: Long?
//        get() {
//            val keycloak = this.buildKeycloak()
//            val token = keycloak.tokenManager().accessTokenString
//            val someUrl = String.format("%s/admin/realms/%s", this.url, realmName)
//            val client: OkHttpClient = OkHttpClient()
//            // Преобразование объекта UserResource в JSON
//            log.info("url = $someUrl")
//            // Создание запроса
//            val request: Request = Builder()
//                .url(someUrl)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .addHeader("Authorization", "Bearer $token")
//                .get()
//                .build()
//
//            // Отправка запроса и получение ответа
//            try {
//                val response: Response = client.newCall(request).execute()
//                if (response.isSuccessful()) {
//                    log.debug("Запрос выполнен успешно!")
//                    val responseBody: String = response.body().string()
//                    log.error("responseBody = $responseBody")
//                    val jsonObject: JsonObject = JsonParser.parseString(responseBody).getAsJsonObject()
//                    val lifetime: Long = jsonObject.get("waitIncrementSeconds").getAsLong()
//                    return lifetime
//                } else {
//                    log.debug("Ошибка при выполнении запроса: " + response.code())
//                }
//                response.close()
//                response.body().close() // Зак
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return null
//        }
//
//
//    fun unblockedUserByUsername(username: String?) {
//        log.info("start unblocked")
//        // TODO [chaetal]: Вернуть (с помощью замыканий?)
//        //        try (var keycloak = this.buildKeycloak()) {
//        val keycloak = this.buildKeycloak()
//        log.info("username admin = $adminUsername")
//        log.info("password admin = $adminPassword")
//        log.info("keycloak $keycloak")
//        log.info("keycloak token " + keycloak.tokenManager().accessTokenString)
//
//        val user = keycloak.realm(realmName).users().search(username, true)[0]
//        log.info("user = $user")
//
//        updateUser(user.id, keycloak.tokenManager().accessTokenString)
//        //        }
//    }
//
//    private fun updateUser(id: String, token: String) {
//        // TODO: Что это за ссылка?
//        val someUrl = String.format(
//            "%s/admin/realms/%s/attack-detection/brute-force/users/%s", this.url,
//            realmName, id
//        )
//        val client: OkHttpClient = OkHttpClient()
//        // Преобразование объекта UserResource в JSON
//        log.info("url = $someUrl")
//        // Создание запроса
//        val request: Request = Builder()
//            .url(someUrl)
//            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//            .addHeader("Authorization", "Bearer $token")
//            .delete()
//            .build()
//
//        // Отправка запроса и получение ответа
//        try {
//            val response: Response = client.newCall(request).execute()
//            if (response.isSuccessful()) {
//                log.debug("Запрос выполнен успешно!")
//            } else {
//                log.debug("Ошибка при выполнении запроса: " + response.code())
//            }
//            response.close()
//            response.body().close() // Зак
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    fun hasAnotherSession(tokenData: TokenData): Boolean {
//        try {
//            val tokenSessionId: Unit = tokenData.extractSessionId()
//
//            val sessions = this.getSessionsOfUserNamed(tokenData.getUsername())
//
//            return sessions.stream().anyMatch { session: UserSessionRepresentation -> session.id != tokenSessionId }
//        } catch (e: TokenData.UnableToExtractSessionId) {
//            return false
//        }
//    }
//
//    fun getUserSessionsCount(username: String?): Int {
//        return getSessionsOfUserNamed(username).size
//    }
//
//    fun getSessionsOfUserNamed(userName: String?): List<UserSessionRepresentation> {
//        return findUserNamed(userName).userSessions
//    }
//
//    fun findUserNamed(userName: String?): UserResource {
//        // TODO [chaetal]: Вернуть (с помощью замыканий?)
//        //        try (var keycloak = this.buildKeycloak()) {
//        val keycloak = this.buildKeycloak()
//        val id = keycloak.realm(realmName).users().search(userName, true)[0].id
//        val realmResource = keycloak.realms().realm(realmName)
//        return realmResource.users()[id]
//        //        }
//    }
//
//    fun logoutUserNamed(userName: String?) {
//        findUserNamed(userName).logout()
//    }
//
//    val realm: RealmResource
//        get() = this.getRealm(this.realmName)
//
//    fun getRealm(realmName: String?): RealmResource {
//        // TODO [chaetal]: Вернуть (с помощью замыканий?)
//        //        try (var keycloak = this.buildKeycloak()) {
//        //            return keycloak.realm(realmName);
//        //        }
//        return buildKeycloak().realm(realmName)
//    }
//


}