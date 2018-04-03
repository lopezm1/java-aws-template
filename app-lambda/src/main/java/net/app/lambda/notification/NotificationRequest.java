package net.app.lambda.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationRequest {

    private long notificationId;
    private long userId;
    private String generated;
    private int notificationType;
    private String message;

}
