package com.grognak.responsehandler;

import com.grognak.IrcClient;

/**
 * Created by Chris on 2/23/2016.
 */
public class ResponseHandlerQuit implements ResponseHandler {
    @Override
    public void handleResponse(IrcClient client, String prefix, String command, String params) {
        if (ResponseHandlerUtils.isRequest("quit", params)) {
            client.sendRaw("QUIT");
        }
    }
}
