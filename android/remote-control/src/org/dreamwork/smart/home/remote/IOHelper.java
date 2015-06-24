package org.dreamwork.smart.home.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: seth.yang
 * Date: 15-3-1
 * Time: 上午5:12
 */
public class IOHelper {
    public static byte[] readLine (InputStream in) throws IOException {
        ByteArrayOutputStream buff = new ByteArrayOutputStream ();
        int last = -1, current;
        while (true) {
            current = in.read ();
            if (current == -1) {
                return buff.size () == 0 ? null : buff.toByteArray ();
            }

            buff.write (current);
            if (current == '\n' && last == '\r') {
                return buff.toByteArray ();
            } else if (current == '\r') {
                last = current;
            }
        }
    }

    static final int MAX = 1024 * 10;

    public static void readMaxSize (InputStream in, OutputStream out, int maxSize) throws IOException {
        byte[] buff = new byte[Math.min (MAX, maxSize)];
        int length, lengthToRead, lengthRead = 0;
        while (lengthRead < maxSize) {
            lengthToRead = maxSize - lengthRead;
            lengthToRead = Math.min (lengthToRead, buff.length);
            length = in.read (buff, 0, lengthToRead);
            if (length == -1)
                return;
            lengthRead += length;

            out.write (buff, 0, length);
        }
    }
}