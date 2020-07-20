package by.itsupportme.security.security

import by.itsupportme.security.repos.AppUserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService (

        @Autowired
        val appUserRepo: AppUserRepo

) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        return appUserRepo.findUserByUsername(username.toString()).orElseThrow { UsernameNotFoundException("$username not found") }
    }
}