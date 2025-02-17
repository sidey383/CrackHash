package ru.sidey383.crackhash.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class APIManagerEndpoint {
    public static final String HASH_CRACK = "/api/hash/crack";
    public static final String HASH_STATUS = "/api/hash/status";
    public static final String INTERNAL_MANAGER_HASH_CRACK_REQUEST = "/internal/api/manager/hash/crack/request";
}
