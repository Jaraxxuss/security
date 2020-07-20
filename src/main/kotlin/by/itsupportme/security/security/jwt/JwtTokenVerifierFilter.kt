package by.itsupportme.security.security.jwt

import com.google.common.base.Strings
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenVerifierFilter(
        private val secretKey: SecretKey,
        private val jwtConfig: JwtConfig
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, responce: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader(jwtConfig.getAuthorizationHeaderName())
        if(Strings.isNullOrEmpty(authorization) || !authorization.startsWith(jwtConfig.tokenPrefix)){
            chain.doFilter(request,responce)
            return
        }

        val token = authorization.substring(7)

        try{

            val parseClaimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(token)

            val body = parseClaimsJws.body
            val username = body.subject
            val authorities = (body["authorities"] as List<Map<String,String>>)
                    .stream()
                    .map { t -> SimpleGrantedAuthority(t["authority"]) }
                    .collect(Collectors.toSet())
            val authentication = UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            )

            SecurityContextHolder.getContext().authentication = authentication

        } catch (e: JwtException){
            throw IllegalStateException("token $token cannot be trust")
        }

        chain.doFilter(request,responce)
    }
}