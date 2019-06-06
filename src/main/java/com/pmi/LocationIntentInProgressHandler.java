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

@Slf4j
public class LocationIntentInProgressHandler implements IntentRequestHandler {
    public static final String KEY_SLOT_CITY = "city";
    public static final String KEY_SLOT_STATE = "state";
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return (handlerInput.matches(intentName("LocationIntent"))
                && intentRequest.getDialogState() == DialogState.IN_PROGRESS);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        log.info("In LocationIntentInProgressHandler");
        Map<String, Slot> slots = intentRequest.getIntent().getSlots();
        Slot citySlot = slots.get(KEY_SLOT_CITY);
        Slot stateSlot = slots.get(KEY_SLOT_STATE);
        String city = citySlot != null && citySlot.getValue() != null ? citySlot.getValue().toLowerCase() : null;
        String state = stateSlot != null && stateSlot.getValue() != null ? stateSlot.getValue().toLowerCase() : null;
        log.info("city slot {}, state slot {}", city, state);
        return handlerInput.getResponseBuilder()
                .addDelegateDirective(intentRequest.getIntent())
                .build();
    }
}