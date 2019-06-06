package com.pmi;


import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

@Slf4j
public class HelpIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "You can say Change Location";
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Change Location", speechText)
                .withReprompt(speechText)
                .build();
    }
}
