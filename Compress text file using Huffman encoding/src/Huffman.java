
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.PriorityQueue;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PC
 */
public class Huffman {

    HuffmanNode root;
    long numChar;
    HashMap<Character, Integer> map1;// store character and frequency
    HashMap<Character, String> map2; //store character and code

    public Huffman() {
    }

    public void clear() {
        this.map1 = new HashMap<>();
        this.map2 = new HashMap<>();
        this.root = null;
        this.numChar = 0;
    }

    // count frequency of character and number of characters
    public void countFrequency(String str) {
        numChar = str.length();
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if (map1.containsKey(c)) {
                map1.put(c, map1.get(c) + 1);
            } else {
                map1.put(c, 1);
            }
        }
    }

    // count frequency of character and number of characters
    public void countFrequency(File f) throws Exception {
        FileReader fr = new FileReader(f);
        int i;
        Character c;
        while ((i = fr.read()) != -1) {
            numChar++;
            c = (char) i;
            if (map1.containsKey(c)) {
                map1.put(c, map1.get(c) + 1);
            } else {
                map1.put(c, 1);
            }
        }
        fr.close();
    }

    public void setCode(HuffmanNode root, String s) {
        if (root.isLeaf()) {
            map2.put(root.getCharacter(), s);
            return;
        }
        setCode(root.getLeftChild(), s + "0");
        setCode(root.getRightChild(), s + "1");
    }

    public void HuffmanEncode() {
        int n = map1.size();
        PriorityQueue<HuffmanNode> q = new PriorityQueue<>(n);
        for (Character i : map1.keySet()) {
            HuffmanNode node = new HuffmanNode();
            node.setCharacter(i);
            node.setFreq(map1.get(i));
            node.setLeftChild(null);
            node.setRightChild(null);
            q.add(node);
        }
        if (q.size() == 1) {
            HuffmanNode x = q.poll();
            root = new HuffmanNode();
            root.setLeftChild(x);
        } else {
            while (q.size() > 1) {
                HuffmanNode x = q.poll();
                HuffmanNode y = q.poll();
                HuffmanNode f = new HuffmanNode();
                f.setFreq(x.getFreq() + y.getFreq());
                f.setLeftChild(x);
                f.setRightChild(y);
                root = f;
                q.add(f);
            }
        }
        setCode(root, "");
    }

    public void printKey() {
        for (Character c : map2.keySet()) {
            switch (c) {
                case '\t':
                    System.out.println("\'\\t\'" + " | " + map2.get(c));
                    break;
                case '\n':
                    System.out.println("\'\\n\'" + " | " + map2.get(c));
                    break;
                case '\r':
                    System.out.println("\'\\r\'" + " | " + map2.get(c));
                    break;
                default:
                    System.out.println("\'" + c + "\'" + "  | " + map2.get(c));
                    break;
            }
        }
    }

    // Function 1
    public void EncodeString() {
        clear();
        String str = Utility.getString("Enter a string: ");
        //count the characters frequency
        countFrequency(str);
        //encode string
        HuffmanEncode();
        System.out.print("Encoded string: ");
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            s = s + map2.get(str.charAt(i));
        }
        System.out.println(s);
        System.out.println("Key: ");
        printKey();
        System.out.println("Encode successful.");
    }

    // Function 2
    // The format of a compressed file: tree -> length of original file -> the encoded string
    public void CompressFile() {
        clear();
        try {
            File f = Utility.readFile("Enter file path: ");
            long originalSize = f.length();
            //count the frequency of character
            countFrequency(f);
            //encode string
            HuffmanEncode();
            File f1 = Utility.createFile("Enter output file path: ");
            BinaryOut.initialize(f1);
            //write tree
            BinaryOut.writeTree(root);
            //write length of original string
            BinaryOut.writeLong(numChar);
            //write encoded string
            FileReader fr = new FileReader(f);
            int i;
            while ((i = fr.read()) != -1) {
                for (char c : map2.get((char) i).toCharArray()) {
                    if (c == '1') {
                        BinaryOut.writeBoolean(true);
                    }
                    if (c == '0') {
                        BinaryOut.writeBoolean(false);
                    }
                }
            }
            fr.close();
            BinaryOut.close();
            long compressSize = f1.length();
            System.out.println("Original size: " + originalSize + " bytes");
            System.out.println("Compress size: " + compressSize + " bytes");
            System.out.println("Compress successful.");
        } catch (Exception e) {
            System.out.println("Cannot read file.");
        }
    }

    //Function 3
    public void DecompressFile() {
        clear();
        File f1 = Utility.readFile("Enter compressed file path: ");
        long compressSize = f1.length();
        File f2 = Utility.createFile("Enter output file path: ");
        BinaryIn.initalize(f1);
        BinaryOut.initialize(f2);
        //read the tree
        root = BinaryIn.readTree();
        //read the original 
        numChar = BinaryIn.readLong();
        //decode using tree
        for (long i = 0; i < numChar; i++) {
            HuffmanNode x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryIn.readBoolean();
                if (bit) {
                    x = x.getRightChild();
                } else {
                    x = x.getLeftChild();
                }
            }
            BinaryOut.writeChar(x.getCharacter());
        }
        BinaryIn.close();
        BinaryOut.close();
        long decompressSize = f2.length();
        System.out.println("Compress size: " + compressSize + " bytes");
        System.out.println("Decompress size: " + decompressSize + " bytes");
        System.out.println("Decompress successful");
    }

}
