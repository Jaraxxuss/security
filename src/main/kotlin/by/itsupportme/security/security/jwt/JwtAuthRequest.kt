package by.itsupportme.security.security.jwt

data class JwtAuthRequest(
        val username: String = "",
        val password: String = ""
)