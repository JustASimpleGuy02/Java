
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
public class BinaryOut {

    private static BufferedOutputStream out; // output stream 
    private static int buffer;               // 8-bit buffer 
    private static int n;                    // number of bits remaining in buffer

    public static void initialize(File f) {
        try {
            out = new BufferedOutputStream(new FileOutputStream(f));
            buffer = 0;
            n = 0;
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private static void writeBit(boolean bit) {
        // add bit to buffer
        buffer <<= 1;
        if (bit) {
            buffer |= 1;
        }
        // if buffer is full (8 bits), write out as a single byte
        n++;
        if (n == 8) {
            clearBuffer();
        }
    }

    private static void writeByte(int x) {
        assert x >= 0 && x < 256;
        // optimized if byte-aligned
        if (n == 0) {
            try {
                out.write(x);
            } catch (IOException e) {
                System.out.println("Can not write");
            }
            return;
        }
        // otherwise write one bit at a time
        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    private static void clearBuffer() {
        if (n == 0) {
            return;
        }
        if (n > 0) {
            buffer <<= (8 - n);
        }
        try {
            out.write(buffer);
        } catch (IOException e) {
            System.out.println("Can not clear buffer");
        }
        n = 0;
        buffer = 0;
    }

    public static void flush() {
        clearBuffer();
        try {
            out.flush();
        } catch (IOException e) {
            System.out.println("Can not flush");
        }
    }

    public static void close() {
        flush();
        try {
            out.close();
        } catch (IOException e) {
            System.out.println("Can not close");
        }
    }

    public static void writeBoolean(boolean x) {
        writeBit(x);
    }

    public static void writeLong(long x) {
        writeByte((int) ((x >>> 56) & 0xff));
        writeByte((int) ((x >>> 48) & 0xff));
        writeByte((int) ((x >>> 40) & 0xff));
        writeByte((int) ((x >>> 32) & 0xff));
        writeByte((int) ((x >>> 24) & 0xff));
        writeByte((int) ((x >>> 16) & 0xff));
        writeByte((int) ((x >>> 8) & 0xff));
        writeByte((int) ((x >>> 0) & 0xff));
    }

    public static void writeChar(char x) {
        if (x < 0 || x >= 256) {
            throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        }
        writeByte(x);
    }

    // r: number of relevant bits in the char
    public static void writeChar(char x, int r) {
        if (r == 8) {
            writeChar(x);
            return;
        }
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x >= (1 << r)) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; i++) {
            boolean bit = ((x >>> (r - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public static void writeTree(HuffmanNode root) {
        if (root.isLeaf()) {
            BinaryOut.writeBoolean(true);
            BinaryOut.writeChar(root.getCharacter(), 8);
            return;
        }
        BinaryOut.writeBoolean(false);
        writeTree(root.getLeftChild());
        writeTree(root.getRightChild());
    }
}
