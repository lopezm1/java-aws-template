package net.app.util.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SNSClient {

    private AmazonSNS client = AmazonSNSClient.builder()
            .withRegion(Regions.US_WEST_2)
            .build();

    public PublishResult publish(String endpointArn, String message) throws EndpointDisabledException {
        // Create publishrequest - accept json object
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setMessageStructure("json");
        publishRequest.setTargetArn(endpointArn);

        // Set the message that will be sent to the endpoint
        log.info("Message Body: " + message);
        publishRequest.setMessage(message);

        return client.publish(publishRequest);
    }

}
