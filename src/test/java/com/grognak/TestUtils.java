package com.grognak;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Chris on 2/24/2016.
 */
public abstract class TestUtils {
    public static String getUserPrefix() {
        return ":ircUser!~username@127.0.0.1";
    }

    public static ArrayList<String> getMessages(Socket mockSocket) {
        return ((MockSocket.MockOutputStream) ((MockSocket)mockSocket).getOutputStream()).getWrittenLines();
    }
}
