package by.itsupportme.security.security.model

import by.itsupportme.security.security.model.Permission
import com.google.common.collect.Sets
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors

enum class Role(val permissions: MutableSet<Permission>) {
    ADMIN(Sets.newHashSet(Permission.CAR_READ, Permission.CAR_WRITE)),
    USER(Sets.newHashSet(Permission.CAR_READ));

    fun getGrantedAuthorities(): MutableSet<SimpleGrantedAuthority> {
        val result = permissions
                .stream()
                .map { permission: Permission -> SimpleGrantedAuthority(permission.persmision) }
                .collect(Collectors.toSet())
        result.add(SimpleGrantedAuthority("ROLE_" + this.name))
        return result
    }
}