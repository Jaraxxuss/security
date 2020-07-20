package by.itsupportme.security.security.jwt

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class JwtConfig(
        val secretKey: String = "securesecuresecuresecuresecuresecure",
        val tokenPrefix: String = "Bearer ",
        val tokenExpiration: Long = 1
) {

    fun getAuthorizationHeaderName() = HttpHeaders.AUTHORIZATION
}