package com.grognak.responsehandler;

import com.grognak.IrcClient;
import com.grognak.IrcEngine;

/**
 * Created by Chris on 2/23/2016.
 */
public class ResponseHandlerPing implements ResponseHandler {
    @Override
    public void handleResponse(IrcClient client, String prefix, String command, String params) {
        if (prefix == null && command.equals("PING")) {
            client.sendRaw("PONG " + params);
        }
    }
}
