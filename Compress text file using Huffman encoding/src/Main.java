
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PC
 */
public class Main {

    public static void main(String[] args) {
        int i;
        Huffman hm = new Huffman();
        //Loops until user want to exit program
        while (true) {
            //display menu
            displayMenu();
            //get choice
            i = Utility.getInt("Enter choice: ", 1, 4);
            switch (i) {
                case 1:
                    //encode a string
                    hm.EncodeString();
                    break;
                case 2:
                    //compress a file
                    hm.CompressFile();
                    break;
                case 3:
                    //decompress a file 
                    hm.DecompressFile();
                    break;
                case 4:
                    //exit program
                    System.out.println("Thank you for using the program.");
                    return;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("-CSD301-------------------");
        System.out.println("Pham Hoang Nam -- HE160714");
        System.out.println("  ------ Huffman -------  ");
        System.out.println("1. Encode a string using Huffman encode.");
        System.out.println("2. Compress a text file using Huffman encode.");
        System.out.println("3. Decompress a compressed file.");
        System.out.println("4. Exit the program.");
    }

}
