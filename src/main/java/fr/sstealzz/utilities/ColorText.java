package fr.sstealzz.utilities;

public class ColorText {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_BLACK = ANSI_BOLD + "\u001B[30m";
    public static final String ANSI_RED = ANSI_BOLD + "\u001B[31m";
    public static final String ANSI_GREEN = ANSI_BOLD + "\u001B[32m";
    public static final String ANSI_YELLOW = ANSI_BOLD + "\u001B[33m";
    public static final String ANSI_BLUE = ANSI_BOLD + "\u001B[34m";
    public static final String ANSI_PURPLE = ANSI_BOLD + "\u001B[35m";
    public static final String ANSI_CYAN = ANSI_BOLD + "\u001B[36m";
    public static final String ANSI_WHITE = ANSI_BOLD + "\u001B[37m";

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}


