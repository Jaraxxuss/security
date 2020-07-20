package by.itsupportme.security.security

import by.itsupportme.security.security.jwt.JwtConfig
import by.itsupportme.security.security.jwt.JwtTokenVerifierFilter
import by.itsupportme.security.security.jwt.JwtUsernamePasswordAuthFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import javax.crypto.SecretKey

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        @Autowired
        val passwordEncoder: PasswordEncoder,
        @Autowired
        val appUserDetailsService: AppUserDetailsService,
        @Autowired
        val secretKey: SecretKey,
        @Autowired
        val jwtConfig: JwtConfig
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http
                ?.csrf()?.disable()
                ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ?.and()
                ?.addFilter(JwtUsernamePasswordAuthFilter(authenticationManager(),secretKey,jwtConfig))
                ?.addFilterAfter(JwtTokenVerifierFilter(secretKey,jwtConfig),JwtUsernamePasswordAuthFilter::class.java)
                ?.authorizeRequests()
                ?.antMatchers("/", "index", "css/**", "js/**","/login")?.permitAll()
                ?.anyRequest()?.authenticated()
                ?.and()
                ?.httpBasic()

    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(appUserDetailsService)

        return provider
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(daoAuthenticationProvider())

    }
}