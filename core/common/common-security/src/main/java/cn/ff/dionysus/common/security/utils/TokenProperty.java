package cn.ff.dionysus.common.security.utils;

import lombok.Data;

@Data
public class TokenProperty {
    private String secret;
    private long expired;
    private long refreshExpired;
}
