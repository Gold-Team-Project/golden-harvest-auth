package com.teamgold.goldenharvestauth.domain.auth.command.application.event.dto;

import lombok.Builder;

@Builder
public record UserStatusUpdatedEvent(
        String email,
        String company,
        String businessNumber,
        String name,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String postalCode
) {}
