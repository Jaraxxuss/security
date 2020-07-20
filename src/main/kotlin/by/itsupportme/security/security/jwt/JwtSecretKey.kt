package by.itsupportme.security.security.jwt

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@Configuration
class JwtSecretKey(
        @Autowired
        private val jwtConfig: JwtConfig
) {
    @Bean
    fun secretKey(): SecretKey = Keys.hmacShaKeyFor(jwtConfig.secretKey.byteInputStream().readBytes())
}