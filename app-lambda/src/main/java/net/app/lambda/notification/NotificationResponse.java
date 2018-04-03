package net.app.lambda.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class NotificationResponse {

    @Getter private final String message;
    @Getter private final NotificationRequest input;

}

