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
public class ResponseHandlerQuitTest {
    ResponseHandler responseHandler;
    Socket mockSocket;
    IrcClient mockClient;

    @Before
    public void before() {
        responseHandler = new ResponseHandlerQuit();
        mockSocket = new MockSocket();
        mockClient = new IrcClient(mockSocket);
    }

    @Test
    public void testHandleResponseValidChannel() throws Exception {
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "PRIVMSG", "#channel :@quit");

        assertThat(TestUtils.getMessages(mockSocket)).containsExactly("QUIT");
    }

    @Test
    public void testHandleResponseInvalidWrongCommand() throws Exception {
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "PRIVMSG", "#channel :@other");

        assertThat(TestUtils.getMessages(mockSocket)).isEmpty();
    }
}