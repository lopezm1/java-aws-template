package net.app.lambda.notification;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationHandler implements RequestHandler<NotificationRequest, NotificationResponse> {

    @Override
    public NotificationResponse handleRequest(NotificationRequest input, Context context){
        log.info("Input recieved: " + input.toString());

        return new NotificationResponse("Your notification message has successfully executed.", input);
    }
}
