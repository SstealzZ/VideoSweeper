package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.sstealzz.data.Data;
import fr.sstealzz.data.FileExplorer;

public class Menu {

    enum State {
        SUCCESS, ERROR
    }

    public void Init(List<File> files, List<Data> datas, FileExplorer fileExplorer) throws IOException {
        printInit(files, datas, fileExplorer);
    }

    private int getFileNotCompressed(List<Data> datas) throws IOException{
        int i = 0;
        System.out.println(datas);
        for (Data data : datas) {
            if (data.isCompressed() == false) {
                i++;
            }
        }
        return i;
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
