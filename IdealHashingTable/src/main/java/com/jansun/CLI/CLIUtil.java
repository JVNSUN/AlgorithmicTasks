package com.jansun.CLI;

import java.util.Scanner;

public class CLIUtil {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int readChoice() {
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
    public static String readWanted() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    public static void printChooseFileMenu() {
        System.out.print("Enter the file name: ");
    }

    public static void waitEnterPress() {
        System.out.println("\nPress ENTER to proceed");
        Scanner in = new Scanner(System.in);
        in.next();
    }
}
