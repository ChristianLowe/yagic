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
public class ResponseHandlerCoinFlipTest {
    ResponseHandler responseHandler;
    Socket mockSocket;
    IrcClient mockClient;

    @Before
    public void before() {
        responseHandler = new ResponseHandlerCoinFlip();
        mockSocket = new MockSocket();
        mockClient = new IrcClient(mockSocket);
    }

    @Test
    public void testHandleResponseIsRandom() throws Exception {
        // 1/562,949,953,421,312 chance of all tails or all heads (good luck!)
        for (int i = 0; i < 50; i++) {
            responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "PRIVMSG", "#channel :@coinflip");
        }

        assertThat(TestUtils.getMessages(mockSocket)).contains(
                "PRIVMSG #channel :You flipped heads!", "PRIVMSG #channel :You flipped tails!"
        );
    }

    @Test
    public void testHandleResponseInvalid() throws Exception {
        responseHandler.handleResponse(mockClient, TestUtils.getUserPrefix(), "PRIVMSG", "#channel :@other");

        assertThat(TestUtils.getMessages(mockSocket)).isEmpty();
    }
}