package com.pmi;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.DialogState;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.pmi.LocationIntentInProgressHandler.KEY_SLOT_CITY;
import static com.pmi.LocationIntentInProgressHandler.KEY_SLOT_STATE;

@Slf4j
public class LocationIntentCompletedHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return (handlerInput.matches(intentName("LocationIntent"))
                && intentRequest.getDialogState() == DialogState.COMPLETED);
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        log.info("In LocationIntentCompletedHandler");
        String speechText;
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        Slot citySlot = slots.get(KEY_SLOT_CITY);
        Slot stateSlot = slots.get(KEY_SLOT_STATE);
        String city = citySlot != null && citySlot.getValue() != null ? citySlot.getValue().toLowerCase() : null;
        String state = stateSlot != null && stateSlot.getValue() != null ? stateSlot.getValue().toLowerCase() : null;
        log.info("city slot {}, state slot {}", city, state);
        boolean locationAcquired = false;
        if (city != null && state != null) {
            locationAcquired = true;
            log.info("user {}, location {}");
        }
        if (locationAcquired) {
            speechText = "You location is set to " + city + ", " + state;
        } else {
            String outputCity = city == null ? "" : city;
            String outputState = state == null ? "" : state;
            String outputCitySpeech = outputCity + ", " + outputState;
            speechText = "Your location " + outputCitySpeech + " is not supported, you can say change location to try again";
        }
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("Location", speechText)
                .withShouldEndSession(false)
                .build();
    }
}