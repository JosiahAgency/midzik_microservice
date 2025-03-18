package com.midziklabs.advertisement.feignclient.model;

import com.google.gson.annotations.JsonAdapter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthUser {
    private Long id;
    private String name;
    private String email;
}
