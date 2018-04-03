package net.app.util.aws;

import java.util.HashMap;
import java.util.Map;

public enum SNSPlatform {

    APNS_SANDBOX("apple"),
    APNS("apple"),
    GCM("android");

    private final String snsPlatform;

    SNSPlatform(final String snsPlatform) { this.snsPlatform = snsPlatform; }

    private static final Map<String, SNSPlatform> _map = new HashMap<String, SNSPlatform>();
    static {
        for(SNSPlatform p: SNSPlatform.values()) {
            _map.put(p.snsPlatform, p);
        }
    }

    public static SNSPlatform getPlatform(String snsPlatform){
        SNSPlatform p = _map.get(snsPlatform);

        if(p == null){
            throw new IllegalArgumentException("SNS Platform Not Supported. GCM and APNS only.");
        }

        return p;
    }
}
