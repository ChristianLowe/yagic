package com.grognak.responsehandler;

/**
 * Created by Chris on 2/23/2016.
 */
public abstract class ResponseHandlerUtils {
    public static boolean isRequest(String requestName, String params) {
        String[] tokens = params.split("\\s+");

        try {
            String userRequest = tokens[1].substring(2, tokens[1].length());

            return requestName.toLowerCase().equals(userRequest.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }
    public static String getRequestAudience(String prefix, String params) {
        String[] tokens = params.split("\\s+");
        char audienceIdentifier = tokens[1].charAt(1);

        if (audienceIdentifier == '.' || audienceIdentifier == '!') {
            String nickname = prefix.substring(1, prefix.indexOf('!'));

            return nickname;
        } else if (audienceIdentifier == '@') {
            String channel = tokens[0];

            return channel;
        } else {
            throw new RuntimeException("Input does not have an audience identifier");
        }
    }
}
