package net.app.util.aws;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.app.core.entities.util.Notification;

public class SNSMessageGenerator {

    /*
     * This message is delivered if a platform specific message is not specified
     * for the end point. It must be set. It is received by the device as the
     * value of the key "default".
     */
    public static final String defaultMessage = "New notification.";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getPlatformMessage(SNSPlatform platform, Notification n) {
        switch (platform) {
            case APNS_SANDBOX:
                return SNSMessageGenerator.createAppleMessage(n);
            case APNS:
                return SNSMessageGenerator.createAppleMessage(n);
            case GCM:
                return SNSMessageGenerator.createAndroidMessage(n);
            default:
                throw new IllegalArgumentException("Platform not supported : "
                        + platform.name());
        }
    }

    private static String jsonify(Object message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw (RuntimeException) e;
        }
    }

    private static String createAndroidMessage(Notification n) {
        Map<String, String> snsMessageMap = new HashMap<String, String>();
        Map<String, Object> androidMessageMap = new HashMap<String, Object>();
        Map<String, Object> innerbody = getInnerBody(n.getMessage(), n.getNotificationCount(), n.getOrderId(), n.getNotificationType());
        androidMessageMap.put("data", innerbody);
        String androidMessage = jsonify(androidMessageMap);
        snsMessageMap.put(SNSPlatform.GCM.name(), androidMessage);
        snsMessageMap.put("default", defaultMessage);
        return jsonify(snsMessageMap);
    }

    private static String createAppleMessage(Notification n) {
        Map<String, String> snsMessageMap = new HashMap<String, String>();
        Map<String, Object> appleMessageMap = new HashMap<String, Object>();
        Map<String, Object> innerbody = getInnerBody(n.getMessage(), n.getNotificationCount(), n.getOrderId(), n.getNotificationType());
        innerbody.put("sound", "default");
        appleMessageMap.put("aps", innerbody);
        String appleMessage = jsonify(appleMessageMap);
        snsMessageMap.put(SNSPlatform.APNS.name(), appleMessage);
        snsMessageMap.put(SNSPlatform.APNS_SANDBOX.name(), appleMessage);
        snsMessageMap.put("default", defaultMessage);
        return jsonify(snsMessageMap);
    }

    private static Map<String, Object> getInnerBody(String alert, int badgeCount, String orderId, String notificationType) {
        Map<String, Object> appMessageMap = new HashMap<String, Object>();
        appMessageMap.put("orderid", orderId);
        appMessageMap.put("badge", badgeCount);
        appMessageMap.put("type", notificationType);
        appMessageMap.put("alert", alert);
        return appMessageMap;
    }
}


