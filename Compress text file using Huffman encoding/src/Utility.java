
import java.io.File;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PC
 */
public class Utility {

    public static int getInt(String msg, int min, int max) {
        Scanner sc = new Scanner(System.in);
        //Loops to validate the input
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine();
            //Check if input is empty
            if (input.isEmpty()) {
                System.out.println("Must not empty.");
                continue;
            }
            try {
                int number = Integer.parseInt(input);
                //Check if number in range [min-max]
                if (min <= number && number <= max) {
                    return number;
                } else {
                    System.out.printf("Must in range [%d-%d]\n", min, max);
                }
            } catch (Exception e) {
                System.out.println("Must be integer.");
            }
        }
    }

    public static String getString(String msg) {
        Scanner sc = new Scanner(System.in);
        // Loops to validate the input
        while (true) {
            System.out.print(msg);
            String input = sc.nextLine();
            // check if input is empty
            if (input.isEmpty()) {
                System.out.println("Must not empty.");
            } else {
                return input;
            }
        }
    }

    public static File readFile(String msg) {
        Scanner sc = new Scanner(System.in);
        // Loops to validate the input
        while (true) {
            System.out.print(msg);
            String filePath = sc.nextLine();
            // Check if filepath is empty
            if (filePath.isEmpty()) {
                System.out.println("File path must not be empty.");
                continue;
            }
            File f = new File(filePath);
            // check if the file does not exist
            if (!f.exists()) {
                System.out.println("File does not exist.");
            } else {
                return f;
            }
        }
    }

    public static File createFile(String msg) {
        Scanner sc = new Scanner(System.in);
        // Loops to validate the input
        while (true) {
            System.out.print(msg);
            String filePath = sc.nextLine();
            // Check if filepath is empty
            if (filePath.isEmpty()) {
                System.out.println("File path must not be empty.");
                continue;
            }
            File f = new File(filePath);
            // check if the file does not exist
            if (f.exists()) {
                System.out.println("File is already existed.");
            } else {
                try {
                    f.createNewFile();
                    return f;
                } catch (Exception e) {
                    System.out.println("Can not create the file.");
                }
            }
        }
    }

}
