package net.app.util.aws;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SNSParallelExecutor {

    /**
     * Publishes notifications a to a list of SNS endpoints in parallel. Waits until final thread before continuing.
     * @param endpoints - list of SNS mobile endpoints
     * @param message - message json that you wish to publish
     */
    public static void publishMessagesInParallel(List<String> endpoints, String message) {

        // Create countdown for # of threads to finish before main thread finishes execution
        CountDownLatch latch = new CountDownLatch(endpoints.size()); // Countdown from endpoints.size() to 0
        ExecutorService executor = Executors.newFixedThreadPool(endpoints.size()); // # of threads in pool

        // Iterate through each endpoint returned from core in parallel threads.
        endpoints.forEach((ep) -> {
            executor.submit(new SNSPublishThread(ep, message, latch));
        });

        try {
            latch.await();
        } catch (InterruptedException e){
            log.error(e.getMessage());
        }
    }
}
