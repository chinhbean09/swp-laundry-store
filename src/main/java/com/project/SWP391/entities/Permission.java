package com.project.SWP391.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    STORE_READ("store:read"),
    STORE_UPDATE("store:update"),
    STORE_CREATE("store:create"),
    STORE_DELETE("store:delete"),

    STAFF_READ("delivery:read"),
    STAFF_UPDATE("delivery:update");

    @Getter
    private final String permission;
}