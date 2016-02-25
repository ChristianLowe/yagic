package com.grognak.responsehandler;

import com.grognak.IrcClient;

/**
 * Created by Chris on 2/23/2016.
 */
public interface ResponseHandler {
    void handleResponse(IrcClient client, String prefix, String command, String params);
}
