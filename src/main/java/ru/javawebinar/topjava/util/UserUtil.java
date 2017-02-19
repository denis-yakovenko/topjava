package ru.javawebinar.topjava.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

/**
 * GKislin
 */
public class UserUtil {

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), newUser.getCaloriesPerDay(), Role.ROLE_USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCaloriesPerDay());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setCaloriesPerDay(userTo.getCaloriesPerDay());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static boolean hasRole(Role role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) return false;
        Authentication authentication = context.getAuthentication();
        if (authentication == null) return false;
        for (GrantedAuthority auth : authentication.getAuthorities()) {if (role.toString().equals(auth.getAuthority())) return true;}
        return false;
    }
}
