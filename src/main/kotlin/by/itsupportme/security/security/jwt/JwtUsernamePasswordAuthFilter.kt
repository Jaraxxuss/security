package by.itsupportme.security.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.LocalDate
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtUsernamePasswordAuthFilter(
        private val authManager: AuthenticationManager,
        private val secretKey: SecretKey,
        private val jwtConfig: JwtConfig

) : UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try{
            val jwtAuthRequest = ObjectMapper().readValue<JwtAuthRequest>(request?.inputStream!!)
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                    jwtAuthRequest.username,
                    jwtAuthRequest.password
            )

            return authManager.authenticate(authentication)
        } catch (e: IOException){
            throw RuntimeException(e)
        }

    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        val token = Jwts.builder()
                .setSubject(authResult?.name)
                .claim("authorities", authResult?.authorities)
                .setIssuedAt(Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.tokenExpiration)))
                .signWith(secretKey)
                .compact()
        response?.addHeader(jwtConfig.getAuthorizationHeaderName(),jwtConfig.tokenPrefix + token)

        super.successfulAuthentication(request, response, chain, authResult)
    }
}