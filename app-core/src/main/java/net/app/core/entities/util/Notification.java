package net.app.core.entities.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private int notificationCount;
    private List<String> deviceEndPoints;
    private String message;
    private String orderId;
    private String notificationId;
    private String notificationType;

}
