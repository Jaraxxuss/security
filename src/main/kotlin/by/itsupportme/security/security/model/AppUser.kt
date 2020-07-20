package by.itsupportme.security.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AppUser(
        private var username: String,
        private var password: String,
        private var authorities: Set<GrantedAuthority>,
        private var isAccountNonExpired: Boolean,
        private var isCredentialsNonExpired: Boolean,
        private var isEnabled: Boolean,
        private var isAccountNonLocked: Boolean


        ) : UserDetails {
    override fun getAuthorities() = authorities

    override fun isEnabled() = isEnabled

    override fun getUsername() = username
    override fun isCredentialsNonExpired() = isCredentialsNonExpired

    override fun getPassword() = password
    override fun isAccountNonExpired() = isAccountNonExpired

    override fun isAccountNonLocked() = isAccountNonLocked


}