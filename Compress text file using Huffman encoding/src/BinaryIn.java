
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PC
 */
public final class BinaryIn {

    private static BufferedInputStream in; // input stream
    private static int buffer;             // 8-bit buffer
    private static int n;                  // number of bits left in buffer

    public static void initalize(File f) {
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            buffer = 0;
            n = 0;
            fillBuffer();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private static void fillBuffer() {
        try {
            buffer = in.read();
            n = 8;
        } catch (IOException e) {
            System.out.println("End of file");
            buffer = -1;
            n = -1;
        }
    }

    public static void close() {
        try {
            in.close();
        } catch (IOException ioe) {
            System.out.println("Could not close the input stream");
        }
    }

    public static boolean readBoolean() {
        n--;
        boolean bit = ((buffer >> n) & 1) == 1;
        if (n == 0) {
            fillBuffer();
        }
        return bit;
    }

    public static char readChar() {
        //special case when aligned byte
        if (n == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }
        //combine last n bits of current buffer with first 8-n bits of new buffer
        int x = buffer;
        x <<= (8 - n);
        int oldN = n;
        fillBuffer();
        n = oldN;
        x |= (buffer >>> n);
        return (char) (x & 0xff);
    }

    // r: number of relevant bits in the char
    public static char readChar(int r) {
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }
        // optimize r = 8 case
        if (r == 8) {
            return readChar();
        }
        char x = 0;
        for (int i = 0; i < r; i++) {
            x <<= 1;
            boolean bit = readBoolean();
            if (bit) {
                x |= 1;
            }
        }
        return x;
    }

    public static long readLong() {
        long x = 0;
        for (int i = 0; i < 8; i++) {
            char c = readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }


    public static HuffmanNode readTree() {
        boolean isLeaf = readBoolean();
        // set the frequency as -1 since it has no use in decode
        if (isLeaf) {
            return new HuffmanNode(readChar(), -1, null, null);
        }else{
            return new HuffmanNode(null, -1, readTree(), readTree());
        }
    }
}
