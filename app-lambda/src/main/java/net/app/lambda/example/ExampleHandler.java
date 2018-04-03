package net.app.lambda.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class ExampleHandler implements RequestHandler<ExampleRequest, ExampleGatewayResponse> {

    @Override
    public ExampleGatewayResponse handleRequest(ExampleRequest input, Context context){
        log.info("Input recieved: " + input.toString());

        ExampleResponse response = new ExampleResponse("Your example message has successfully executed." + new Date(), input);
        Map<String, String> header = new HashMap<>();
        header.put("X-Powered-By", "AWS Lambda & Serverless");
        header.put("Content-Type", "application/json");

        return ExampleGatewayResponse.builder()
                .statusCode(200)
                .body(response.toString())
                .headers(header)
                .build();
    }
}
