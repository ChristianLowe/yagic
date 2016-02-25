package com.grognak.responsehandler;

import com.grognak.IrcClient;

import java.util.Random;

/**
 * Created by Chris on 2/23/2016.
 */
public class ResponseHandlerCoinFlip implements ResponseHandler {
    @Override
    public void handleResponse(IrcClient client, String prefix, String command, String params) {
        if (ResponseHandlerUtils.isRequest("coinflip", params)) {
            String audience;
            try {
                audience = ResponseHandlerUtils.getRequestAudience(prefix, params);
            } catch (RuntimeException e) { return; }

            Random random = new Random();
            if (random.nextBoolean()) {
                client.sendMessage(audience, "You flipped heads!");
            } else {
                client.sendMessage(audience, "You flipped tails!");
            }
        }
    }
}
