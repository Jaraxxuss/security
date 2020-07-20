package by.itsupportme.security.repos.impl

import by.itsupportme.security.repos.AppUserRepo
import by.itsupportme.security.security.model.AppUser
import by.itsupportme.security.security.model.Role
import com.google.common.collect.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class FakeAppUserRepo(
        @Autowired
        private val passwordEncoder: PasswordEncoder
) : AppUserRepo {

    override fun findUserByUsername(username: String): Optional<AppUser> = getAppUsers()
                .stream()
                .filter { t: AppUser -> t.username == username }
                .findFirst()


    private fun getAppUsers() = Lists.newArrayList<AppUser>(
                AppUser(
                        "root",
                        passwordEncoder.encode("root"),
                        Role.ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                AppUser(
                        "toor",
                        passwordEncoder.encode("toor"),
                        Role.USER.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        )

}