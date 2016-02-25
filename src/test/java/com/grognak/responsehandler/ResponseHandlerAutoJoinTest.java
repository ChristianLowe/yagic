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
public class ResponseHandlerAutoJoinTest {
    ResponseHandler responseHandler;
    Socket mockSocket;
    IrcClient mockClient;

    @Before
    public void before() {
        responseHandler = new ResponseHandlerAutoJoin();
        mockSocket = new MockSocket();
        mockClient = new IrcClient(mockSocket);
    }

    @Test
    public void testHandleResponseValid() throws Exception {
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "376", "anyParams");

        TestUtils.getMessages(mockSocket).forEach(message -> assertThat(message).startsWith("JOIN "));
    }

    @Test
    public void testHandleResponseInvalid() throws Exception {
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "PRIVMSG", "#channel :@other");

        assertThat(TestUtils.getMessages(mockSocket)).isEmpty();
    }
}