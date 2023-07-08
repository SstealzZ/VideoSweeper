package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
                    long start = System.currentTimeMillis();
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
                    long end = System.currentTimeMillis();
                    getTime(start, end);
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
                    ColorText.SeparateTerminalLine(State.SUCCESS, "Duplicated data");
                    printDuplicateData(datas, json);
                    ColorText.SeparateTerminalLine(State.SUCCESS, "End of Duplicated data");
                    break;
                case "5":
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "Data Info");
                    printInfo(datas, fileExplorer);
                    ColorText.SeparateTerminalLine(State.SUCCESS, "End of info");
                    break;
                case "6":
                    ColorText.clearScreen();
                    ColorText.SeparateTerminalLine(State.SUCCESS, "Exit");
                    System.exit(0);
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

    private void printDuplicateData(List<Data> datas, Json json) throws IOException {
        List<Data> dataList = json.getData(); // Récupérer la liste de données une seule fois
    
        dataList.forEach(data -> {
            try {
                if (json.isAlreadyExist(data)) {
                    System.out.println(ColorText.ANSI_RED + "[**] " + data.getNameFile() + ColorText.ANSI_RESET);
                    System.out.println(ColorText.ANSI_YELLOW + "[\u25BA] " + data.getPathFile() + ColorText.ANSI_RESET);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    

    private void printInfo(List<Data> datas, FileExplorer fileExplorer) throws IOException {
        Integer count = datas.size();
        
        System.out.println(ColorText.ANSI_CYAN + "Video files found: " + count + ColorText.ANSI_RESET);
        System.out.println(ColorText.ANSI_CYAN + "All Size: " + getAllSize(datas) + " Go");
        System.out.println(ColorText.ANSI_CYAN +"Average Size save: " + (100 - getCompressedRatioPercent(datas)) + "%" + ColorText.ANSI_WHITE + " (" + ColorText.ANSI_RED + ((int) getOriginUnCompressedSize(datas)) + "Go" + ColorText.ANSI_WHITE + ")" + ColorText.ANSI_WHITE + " ->" + ColorText.ANSI_WHITE + " (" + ColorText.ANSI_GREEN + ((int) getAllCompressedSize(datas)) + "Go" + ColorText.ANSI_WHITE + ")" + ColorText.ANSI_RESET);
        System.out.println(ColorText.ANSI_RED + "Uncompressed Size: " + getAllUnCompressedSize(datas) + " Go" + ColorText.ANSI_RESET);
        System.out.println(ColorText.ANSI_GREEN + "Compressed Size: " + getAllCompressedSize(datas) + " Go" + ColorText.ANSI_RESET);


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
        ColorText.SeparateTerminalLine(State.SUCCESS, "Init Success, Now you have to choose an option !!" + "\n" + ColorText.ANSI_CYAN + "All video size : " + " (" + getAllSize(datas) + " GB) " + ColorText.ANSI_RESET);
    }

    private void printChoice() {
        System.out.println(ColorText.ANSI_GREEN + "[1] - Compress all video files not compressed");
        System.out.println(ColorText.ANSI_YELLOW  + "[2] - List all video files found");
        System.out.println(ColorText.ANSI_YELLOW  + "[3] - List all video files not compressed");
        System.out.println(ColorText.ANSI_YELLOW  + "[4] - Duplicated data ?");
        System.out.println(ColorText.ANSI_YELLOW  + "[5] - Data Info");
        System.out.println(ColorText.ANSI_RED  + "[6] - Exit" + ColorText.ANSI_RESET);
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

    private float getCompressedRatioPercent(List<Data> datas) throws IOException {
        float compress_size = getAllCompressedSize(datas);
        float uncompress_size = getOriginUnCompressedSize(datas);

        float ratio = (compress_size / uncompress_size) * 100;
        return Math.round(ratio * 100.0f) / 100.0f;
    }
    
    private int getCountCompressedFile(List<Data> datas) throws IOException {
        int i = 0;
        for (Data data : datas) {
            if (data.isCompressed() == true) {
                i++;
            }
        }
        return i;
    }

    private int getCountUncompressedFile(List<Data> datas) throws IOException {
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
            if (data.isCompressed() == false)
                totalSizeInBytes += data.getSizeFile();
            else
                totalSizeInBytes += data.getCompressedSizeFile();
        }
        float totalSizeInKB = totalSizeInBytes / 1000.0f;
        float totalSizeInGB = totalSizeInKB / 1000000.0f;
        totalSizeInGB = Math.round(totalSizeInGB * 1000.0f) / 1000.0f;
        return totalSizeInGB;
    }

    private float getOriginUnCompressedSize(List<Data> datas) throws IOException {
        long totalUnCompressedSizeInBytes = 0;
        for (Data data : datas) {
            totalUnCompressedSizeInBytes += data.getSizeFile();
        }
        float totalUnCompressedSizeInKB = totalUnCompressedSizeInBytes / 1000.0f;
        float totalUnCompressedSizeInGB = totalUnCompressedSizeInKB / 1000000.0f;
        totalUnCompressedSizeInGB = Math.round(totalUnCompressedSizeInGB * 1000.0f) / 1000.0f;
        return totalUnCompressedSizeInGB;
    }

    private float getAllUnCompressedSize(List<Data> datas) throws IOException {
        long totalUnCompressedSizeInBytes = 0;
        for (Data data : datas) {
            if (data.isCompressed() == false)
                totalUnCompressedSizeInBytes += data.getSizeFile();
        }
        float totalUnCompressedSizeInKB = totalUnCompressedSizeInBytes / 1000.0f;
        float totalUnCompressedSizeInGB = totalUnCompressedSizeInKB / 1000000.0f;
        totalUnCompressedSizeInGB = Math.round(totalUnCompressedSizeInGB * 1000.0f) / 1000.0f;
        return totalUnCompressedSizeInGB;
    }

    private float getAllCompressedSize(List<Data> datas) throws IOException {
        long totalCompressedSizeInBytes = 0;
        for (Data data : datas) {
            if (data.isCompressed() == true)
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

    private void getTime(long startTime, long endTime) {
        long duration = (endTime - startTime);
        long durationInMs = TimeUnit.MILLISECONDS.toMillis(duration);
        long durationInSec = TimeUnit.MILLISECONDS.toSeconds(duration);
        long durationInMin = TimeUnit.MILLISECONDS.toMinutes(duration);
        long durationInHour = TimeUnit.MILLISECONDS.toHours(duration);
        long durationInDay = TimeUnit.MILLISECONDS.toDays(duration);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(ColorText.ANSI_CYAN + "Time : " + durationInDay + "d " + durationInHour + "h " + durationInMin + "m " + durationInSec + "s " + durationInMs + "ms" + ColorText.ANSI_RESET);
    }
    
}
