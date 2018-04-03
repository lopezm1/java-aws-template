package net.app.util.aws;

import com.amazonaws.services.sns.model.EndpointDisabledException;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Data
@AllArgsConstructor
public class SNSPublishThread extends Thread {

    private String endpointArn;
    private String message;
    private CountDownLatch latch;

    public void run() {
        log.info("Thread " + Thread.currentThread().getId() + " - Enter run: " + endpointArn);
        SNSClient client = new SNSClient();

        try {
            PublishResult result = client.publish(endpointArn, message);
            log.info("Thread " + Thread.currentThread().getId() + " - Message published: " + result.getMessageId());
        } catch (EndpointDisabledException | InvalidParameterException se) {
            log.error("Error code: " + se.getErrorCode());
            log.error("Error message: " + se.getErrorMessage() + " (" + endpointArn + ")");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }
}
