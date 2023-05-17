package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import fr.sstealzz.data.Data;
import fr.sstealzz.data.FileExplorer;
import fr.sstealzz.data.Json;

public class Menu {

    enum State {
        SUCCESS, ERROR
    }

    public void Init(List<File> files, List<Data> datas, FileExplorer fileExplorer) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Json json = new Json("config.json");
        ColorText.clearScreen();
        printInit(files, datas, fileExplorer);
        while(true) {
            printChoice();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    ColorText.clearScreen();
                    SeparateTerminalLine(State.SUCCESS, "Compressing all video files not compressed");
                    break;
                case "2":
                    ColorText.clearScreen();
                    SeparateTerminalLine(State.SUCCESS, "List all video files found");
                    json.getData().forEach(data -> {
                        if (data.isCompressed())
                            System.out.println(ColorText.ANSI_GREEN + "[OK] " + data.getNameFile() + ColorText.ANSI_RESET);
                        else
                            System.out.println(ColorText.ANSI_RED + "[**] " + data.getNameFile() + ColorText.ANSI_RESET);
                    });
                    SeparateTerminalLine(State.SUCCESS, "End of list");
                    break;
                case "3":
                    ColorText.clearScreen();
                    SeparateTerminalLine(State.SUCCESS, "List all video files not compressed");
                    SeparateTerminalLine(State.SUCCESS, "End of list");
                    break;
                case "4":
                    ColorText.clearScreen();
                    SeparateTerminalLine(State.SUCCESS, "Exit");
                    System.exit(0);
                    break;
                default:
                    ColorText.clearScreen();
                    SeparateTerminalLine(State.ERROR, "Invalid choice");
                    break;
            }
        }
    }

    private void printInit(List<File> files, List<Data> datas, FileExplorer fileExplorer) throws IOException {
        Integer count = fileExplorer.getCount();
        Integer fileNotCompressed = getFileNotCompressed(datas);
        System.out.println(ColorText.ANSI_YELLOW + "Hello beautiful User !" + "\n" + "Welcome to VideoSweeper !" + ColorText.ANSI_RESET);
        if (count != 0) {
            System.out.println(ColorText.ANSI_GREEN + "You have " + count + " new video files found !" + ColorText.ANSI_RESET);
        }
        if (fileNotCompressed != 0) {
            System.out.println(ColorText.ANSI_RED + "You have " + fileNotCompressed + " video files not compressed !" + ColorText.ANSI_RESET);
        }
        SeparateTerminalLine(State.SUCCESS, "Init Success, Now you have to choose an option !! ");
        
    }

    private void printChoice() {
        System.out.println(ColorText.ANSI_GREEN + "[1] - Compress all video files not compressed");
        System.out.println(ColorText.ANSI_YELLOW  + "[2] - List all video files found");
        System.out.println(ColorText.ANSI_YELLOW  + "[3] - List all video files not compressed");
        System.out.println(ColorText.ANSI_RED  + "[4] - Exit" + ColorText.ANSI_RESET);
    }

    private int getFileNotCompressed(List<Data> datas) throws IOException{
        int i = 0;
        for (Data data : datas) {
            if (data.isCompressed() == false) {
                i++;
            }
        }
        return i;
    }

    private void SeparateTerminalLine(State state, String text){
        System.out.print("------------------------------------------------------------------------");
        if (state == State.ERROR) {
            System.out.print(ColorText.ANSI_RED + "\n" + text + "\n" + ColorText.ANSI_RESET);
            System.out.println("------------------------------------------------------------------------");
        }
        else if (state == State.SUCCESS) {
            System.out.print(ColorText.ANSI_GREEN + "\n" + text + "\n" + ColorText.ANSI_RESET);
            System.out.println("------------------------------------------------------------------------");
        }
        else {

            System.out.print(ColorText.ANSI_YELLOW + "\n" + text + "\n" + ColorText.ANSI_RESET);
            System.out.println("------------------------------------------------------------------------");
        }
    }
    
}
