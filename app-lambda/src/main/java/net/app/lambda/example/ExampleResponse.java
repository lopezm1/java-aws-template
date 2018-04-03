package net.app.lambda.example;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class ExampleResponse {

    @Getter private final String message;
    @Getter private final ExampleRequest input;

}
