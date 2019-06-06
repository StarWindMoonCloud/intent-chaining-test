package com.pmi;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.DialogState;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

@Slf4j
public class LocationIntentStartedHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return (handlerInput.matches(intentName("LocationIntent"))
                && intentRequest.getDialogState() == DialogState.STARTED);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        log.info("In LocationIntentStartedHandler");
        return handlerInput.getResponseBuilder()
                .addDelegateDirective(intentRequest.getIntent())
                .build();
    }
}