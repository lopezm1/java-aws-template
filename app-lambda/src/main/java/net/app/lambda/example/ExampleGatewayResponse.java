package net.app.lambda.example;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ExampleGatewayResponse {

    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;
    private final boolean isBase64Encoded;

}
