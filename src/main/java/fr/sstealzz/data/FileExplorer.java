package fr.sstealzz.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.sstealzz.utilities.ColorText;
import fr.sstealzz.utilities.ColorText.State;

public class FileExplorer {

    private int count;

    public List<File> findVideoFiles() {
        List<File> videoFiles = new ArrayList<>();
        File currentDirectory = new File(".");
        findVideoFilesRecursive(currentDirectory, videoFiles);
        return videoFiles;
    }

    private void findVideoFilesRecursive(File directory, List<File> videoFiles) {
        File[] files = directory.listFiles();
        Json json = new Json("config.json");
        ColorText.clearScreen();
        if (getCount() == 0) {
            ColorText.clearScreen();
            ColorText.SeparateTerminalLine(State.SUCCESS, "Initialization of FileList");
        }
        if (getCount() != 0) {
            ColorText.clearScreen();
            ColorText.SeparateTerminalLine(State.SUCCESS, "Initialization of FileFinder -- New File found: " + getCount());
        }
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findVideoFilesRecursive(file, videoFiles);
                } else if (file.getName().endsWith(".mkv") || file.getName().endsWith(".mp4")) {
                    if (json.isExistName(file.getName()) == false) {
                        Data data = new Data("null", "null", 0, 0);
                        videoFiles.add(file);
                        data.setNameFile(file.getName());
                        data.setPathFile(file.getAbsolutePath());
                        data.setSizeFile(file.length());
                        data.setCompressedSizeFile(file.length() + 1); // data is not compressed here add + 1 for future condition
                        if (data.isCorrupted(data.getNameFile()) == true) {
                            continue;
                        }
                        else {
                            json.append(data);
                            addCount();
                        }
                    }
                }
            }
        }
    }

    public static String findVideofileByName(String name) {
        File currentDirectory = new File(".");
        return findVideoFilesRecursiveByName(currentDirectory, name);
    }
    
    private static String findVideoFilesRecursiveByName(File directory, String name) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String filePath = findVideoFilesRecursiveByName(file, name);
                    if (filePath != null) {
                        return filePath;
                    }
                } else if (file.getName().equals(name)) {
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }    

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount() {
        this.count++;
    }
}

