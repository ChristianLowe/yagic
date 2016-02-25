package com.grognak.responsehandler;

import com.grognak.IrcClient;

/**
 * Created by Chris on 2/23/2016.
 */
public class ResponseHandlerLogIn implements ResponseHandler {
    @Override
    public void handleResponse(IrcClient client, String prefix, String command, String params) {
        if(isServer(client, prefix) && params.contains(":*** Found your hostname")) {
            client.sendLogin();
        }
    }

    private boolean isServer(IrcClient client, String prefix) {
        String hostname = client.getHostname();
        if (hostname.indexOf('.') != hostname.lastIndexOf('.') && !Character.isDigit(hostname.charAt(0))) {
            hostname = hostname.substring(hostname.indexOf('.')+1, hostname.length());
        }
        return prefix.contains(hostname);
    }
}
