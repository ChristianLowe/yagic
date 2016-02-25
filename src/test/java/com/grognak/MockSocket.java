package com.grognak;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Chris on 2/24/2016.
 */
public class MockSocket extends Socket {
    OutputStream outputStream;

    public MockSocket() {
        outputStream = new MockOutputStream();
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(new byte[]{});
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public class MockOutputStream extends OutputStream {
        ArrayList<Character> charBuffer;
        ArrayList<String> writtenLines;

        public MockOutputStream() {
            charBuffer = new ArrayList<>();
            writtenLines = new ArrayList<>();
        }

        @Override
        public void write(int b) throws IOException {
            if (b == '\n') {
                StringBuilder stringBuilder = new StringBuilder(charBuffer.size());
                for (Character character : charBuffer) {
                    if (character != '\r') {
                        stringBuilder.append(character);
                    }
                }
                charBuffer.clear();
                writtenLines.add(stringBuilder.toString());
            } else {
                charBuffer.add((char)b);
            }
        }

        public ArrayList<String> getWrittenLines() {
            return writtenLines;
        }
    }
}
