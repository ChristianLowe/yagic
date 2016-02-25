package com.grognak.responsehandler;

import com.grognak.IrcClient;
import com.grognak.MockSocket;
import com.grognak.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Chris on 2/24/2016.
 */
public class ResponseHandlerLogInTest {
    ResponseHandler responseHandler;
    Socket mockSocket;
    IrcClient mockClient;

    @Before
    public void before() {
        responseHandler = new ResponseHandlerLogIn();
        mockSocket = new MockSocket();
        mockClient = new IrcClient(mockSocket);
    }

    @Test
    public void testHandleResponseValidMainServer() throws Exception {
        mockClient.setHostname("ircserver.net");
        responseHandler.handleResponse(mockClient, ":ircserver.net", "NOTICE", ":*** Found your hostname");

        assertThat(getMessages()).containsExactly("PASS password", "NICK nick", "USER username 0 * :realname");
    }

    @Test
    public void testHandleResponseValidSubServer() throws Exception {
        mockClient.setHostname("ircserver.net");
        responseHandler.handleResponse(mockClient, ":subdomain.ircserver.net", "NOTICE", ":*** Found your hostname");

        assertThat(getMessages()).containsExactly("PASS password", "NICK nick", "USER username 0 * :realname");
    }

    @Test
    public void testHandleResponseValidIP() throws Exception {
        mockClient.setHostname("867.5.309");
        responseHandler.handleResponse(mockClient, ":867.5.309", "NOTICE", ":*** Found your hostname");

        assertThat(getMessages()).containsExactly("PASS password", "NICK nick", "USER username 0 * :realname");
    }

    @Test
    public void testHandleResponseInvalid() throws Exception {
        mockClient.setHostname("ircserver.net");
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "NOTICE", ":*** Found your hostname");

        assertThat(getMessages()).isEmpty();
    }

    private ArrayList<String> getMessages() {
        try {
            return ((MockSocket.MockOutputStream) mockSocket.getOutputStream()).getWrittenLines();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}