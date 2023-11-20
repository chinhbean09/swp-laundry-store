package com.project.thelaundry.security.utils;

import com.project.thelaundry.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getPrincipal() {
        return (User) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
    }
}