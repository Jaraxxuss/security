package by.itsupportme.security.repos

import by.itsupportme.security.security.model.AppUser
import java.util.*

interface AppUserRepo {
    fun findUserByUsername(username : String) : Optional<AppUser>
}