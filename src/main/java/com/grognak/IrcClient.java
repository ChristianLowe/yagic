package com.grognak;

import java.io.*;
import java.net.Socket;

/**
 * Created by Chris on 2/23/2016.
 */
public class IrcClient {
    protected String hostname;
    private Integer port;
    private String nickname;
    private String password;
    private String username;
    private String realname;

    private Socket socket;
    private BufferedReader input;
    private OutputStreamWriter output;

    public IrcClient(Socket socket) {
        this(socket, "nick", "password", "username", "realname");
    }

    public IrcClient(String address, String nick, String password, String username, String realname) {
        setAddress(address);
        setUser(nick, password, username, realname);
        initializeStreams();
    }
    public IrcClient(Socket socket, String nick, String password, String username, String realname) {
        this.socket = socket;
        setUser(nick, password, username, realname);
        initializeStreams();
    }

    private void setAddress(String address) {
        try {
            this.hostname = address.substring(0, address.indexOf(':'));
            this.port = Integer.parseInt(address.substring(address.indexOf(':')+1, address.length()));
        } catch (Exception e) {
            throw new RuntimeException("Cannot parse address, must be in format hostname:port");
        }
    }

    private void setUser(String nickname, String password, String username, String realname) {
        this.nickname = nickname;
        this.password = password;
        this.username = username;
        this.realname = realname;
    }

    private void initializeStreams() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new OutputStreamWriter(new DataOutputStream(socket.getOutputStream()), "US-ASCII");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to initialize socket streams."));
        }
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void sendLogin() {
        sendRaw("PASS " + password);
        sendRaw("NICK " + nickname);
        sendRaw("USER " + username + " 0 * :" + realname);
    }

    public BufferedReader getBufferedReader() {
        return input;
    }

    public void sendRaw(String message) {
        try {
            System.out.println("[RAW] " + message);
            output.write(message + "\r\n");
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinChannel(String channel) {
        sendRaw("JOIN " + channel);
    }

    public void sendMessage(String recipient, String message) {
        sendRaw("PRIVMSG " + recipient + " :" + message);
    }
}
