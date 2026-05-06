package com.workforce.tracker.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshRequest {
    private String refreshToken;
}
