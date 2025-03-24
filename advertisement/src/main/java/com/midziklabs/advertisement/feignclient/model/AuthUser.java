package com.midziklabs.advertisement.feignclient.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthUser {
    private Long id;
    private String name;
    private String email;
}
