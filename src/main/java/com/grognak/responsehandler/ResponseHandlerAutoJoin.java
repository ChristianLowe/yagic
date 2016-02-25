package com.grognak.responsehandler;

import com.grognak.IrcClient;

/**
 * Created by Chris on 2/23/2016.
 */
public class ResponseHandlerAutoJoin implements ResponseHandler {
    @Override
    public void handleResponse(IrcClient client, String prefix, String command, String params) {
        if (command.equals("376")) {
            client.joinChannel("#datchannelthoo");
        }
    }
}
