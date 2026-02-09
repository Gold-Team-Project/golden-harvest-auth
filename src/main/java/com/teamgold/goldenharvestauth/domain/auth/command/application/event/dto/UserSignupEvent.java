package com.teamgold.goldenharvestauth.domain.auth.command.application.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupEvent {
    private String email;
    private String name;
    private String company;
}
