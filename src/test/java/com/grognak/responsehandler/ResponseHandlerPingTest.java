package com.grognak.responsehandler;

import com.grognak.IrcClient;
import com.grognak.MockSocket;
import com.grognak.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Chris on 2/24/2016.
 */
public class ResponseHandlerPingTest {
    ResponseHandler responseHandler;
    Socket mockSocket;
    IrcClient mockClient;

    @Before
    public void before() {
        responseHandler = new ResponseHandlerPing();
        mockSocket = new MockSocket();
        mockClient = new IrcClient(mockSocket);
    }

    @Test
    public void testHandleResponseValid() throws Exception {
        responseHandler.handleResponse(mockClient, null, "PING", ":ircserver.net");

        assertThat(TestUtils.getMessages(mockSocket)).containsExactly("PONG :ircserver.net");
    }

    @Test
    public void testHandleResponseInvalidNonNullPrefix() throws Exception {
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "PING", ":ircserver.net");

        assertThat(TestUtils.getMessages(mockSocket)).isEmpty();
    }

    @Test
    public void testHandleResponseInvalidWrongCommand() throws Exception {
        responseHandler.handleResponse(mockClient, null, "SOMETHING_ELSE", ":ircserver.net");

        assertThat(TestUtils.getMessages(mockSocket)).isEmpty();
    }
}