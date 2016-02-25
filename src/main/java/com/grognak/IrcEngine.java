package com.grognak;

import com.grognak.responsehandler.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Chris on 2/23/2016.
 */
public class IrcEngine {
    private ResponseHandler[] responseHandlers =
        {
                new ResponseHandlerAutoJoin(),
                new ResponseHandlerCoinFlip(),
                new ResponseHandlerLogIn(),
                new ResponseHandlerPing(),
                new ResponseHandlerQuit()
        };

    public IrcEngine() {
        IrcClient client = new IrcClient("chat.freenode.net:6667",
                "BotName", "BotPassword", "Username", "Realname");

        responseLoop(client);
    }

    private void responseLoop(IrcClient client) {
        BufferedReader bufferedReader = client.getBufferedReader();
        String line;

        try {
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] tokens = line.split("\\s+");
                String prefix;
                String command;
                String params;

                if (tokens[0].charAt(0) == ':') {
                    prefix = tokens[0];
                    command = tokens[1];
                    params = String.join(" ", Arrays.copyOfRange(tokens, 2, tokens.length));
                } else {
                    prefix = null;
                    command = tokens[0];
                    params = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
                }

                for (ResponseHandler responseHandler : responseHandlers) {
                    responseHandler.handleResponse(client, prefix, command, params);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
