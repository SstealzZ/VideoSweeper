package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import fr.sstealzz.data.Data;
import fr.sstealzz.data.FileExplorer;
import fr.sstealzz.data.Json;
import fr.sstealzz.utilities.ColorText.State;

public class Menu {

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
                    ColorText.SeparateTerminalLine(State.SUCCESS, "Compressing all video files not compressed");
                    datas.forEach(data -> {
                        if (!data.isCompressed()) {
                            try {
                                Compressor.compress_mkv(data.getPathFile());
                                data.setCompressedSizeFile(new File(data.getPathFile()).length());
                                json.write(datas);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "End of compression");
                    break;
                case "2":
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "List all video files found");
                    json.getData().forEach(data -> {
                        if (data.isCompressed())
                            System.out.println(getAnsiColor(data) + "[OK] " + data.getNameFile() + ColorText.ANSI_WHITE + " (" + ColorText.ANSI_RED + data.getSizeFileInGo() + "Go" + ColorText.ANSI_WHITE + ")" + ColorText.ANSI_WHITE + " ->" + ColorText.ANSI_WHITE + " (" + ColorText.ANSI_GREEN + data.getCompressedSizeFileInGo() + "Go" + ColorText.ANSI_WHITE + ")" + ColorText.ANSI_RESET);
                        else if (data.isCompressedBigger())
                            System.out.println(ColorText.ANSI_YELLOW + "[!!] " + data.getNameFile() + ColorText.ANSI_RESET);
                        else
                            System.out.println(ColorText.ANSI_RED + "[**] " + data.getNameFile() + ColorText.ANSI_WHITE + " (" + ColorText.ANSI_RED + data.getSizeFileInGo() + "Go" + ColorText.ANSI_WHITE + ")" + ColorText.ANSI_RESET);
                    });
                    ColorText.SeparateTerminalLine(State.SUCCESS, "End of list");
                    break;
                case "3":
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "List all video files not compressed");
                    json.getData().forEach(data -> {
                        if (!data.isCompressed())
                            System.out.println(ColorText.ANSI_RED + "[**] " + data.getNameFile() + ColorText.ANSI_WHITE + " (" + ColorText.ANSI_RED + data.getSizeFileInGo() + "Go" + ColorText.ANSI_WHITE + ")" + ColorText.ANSI_RESET);
                    });
                    ColorText.SeparateTerminalLine(State.SUCCESS, "End of list");
                    break;
                case "4":
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "Exit");
                    System.exit(0);
                    break;
                case "99":
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "Check if data is corrupted");
                    json.getData().forEach(data -> {
                        if (data.isCorrupted(data.getNameFile()))
                            System.out.println(ColorText.ANSI_RED + "[**] " + data.getNameFile() + ColorText.ANSI_RESET);
                    });
                    ColorText.SeparateTerminalLine(State.SUCCESS, "End of list");
                    break;
                default:
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.ERROR, "Invalid choice");
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
            System.out.println(ColorText.ANSI_CYAN + "You have " + fileNotCompressed + " video files not compressed !" + ColorText.ANSI_RESET + " (" + getAllCompressedSize(datas) + " GB)");
        }
        ColorText.SeparateTerminalLine(State.SUCCESS, "Init Success, Now you have to choose an option !!" + "\n" + ColorText.ANSI_CYAN + "All video size : " + " (" + getAllSize(datas) + " GB)" + ColorText.ANSI_RESET);
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
    
    private float getAllSize(List<Data> datas) throws IOException {
        long totalSizeInBytes = 0;
        for (Data data : datas) {
            totalSizeInBytes += data.getSizeFile();
        }
        float totalSizeInKB = totalSizeInBytes / 1000.0f;
        float totalSizeInGB = totalSizeInKB / 1000000.0f;
        totalSizeInGB = Math.round(totalSizeInGB * 1000.0f) / 1000.0f;
        return totalSizeInGB;
    }

    private float getAllCompressedSize(List<Data> datas) throws IOException {
        long totalCompressedSizeInBytes = 0;
        for (Data data : datas) {
            if (data.isCompressed() == false)
                totalCompressedSizeInBytes += data.getCompressedSizeFile();
        }
        float totalCompressedSizeInKB = totalCompressedSizeInBytes / 1000.0f;
        float totalCompressedSizeInGB = totalCompressedSizeInKB / 1000000.0f;
        totalCompressedSizeInGB = Math.round(totalCompressedSizeInGB * 1000.0f) / 1000.0f;
        return totalCompressedSizeInGB;
    }
    
    private String getAnsiColor(Data data) {
        if (data.isCompressedEqual())
            return ColorText.ANSI_YELLOW;
        else {
            if (data.isCompressed())
                return ColorText.ANSI_GREEN;
            else
                return ColorText.ANSI_RED;
        }
    }

}
