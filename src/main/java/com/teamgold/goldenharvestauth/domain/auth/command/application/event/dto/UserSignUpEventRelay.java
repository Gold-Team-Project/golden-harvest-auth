package com.teamgold.goldenharvestauth.domain.auth.command.application.event.dto;

import com.teamgold.goldenharvestauth.common.kafka.EventProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserSignUpEventRelay {

    private final EventProducerService producer;

    @Async
    @EventListener
    public void handle(UserSignupEvent event) {
        log.info("유저 회원가입 이벤트 수신, kafka producing...");

        producer.send("user.signup.event", event);
    }
}
