package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.sstealzz.data.Data;
import fr.sstealzz.data.FileExplorer;

public class Init {

    public void Init(List<File> files, List<Data> datas, FileExplorer fileExplorer) throws IOException {
        Integer count = fileExplorer.getCount();
        Integer fileNotCompressed = getFileNotCompressed(datas);
        System.out.println(ColorText.ANSI_YELLOW + "Hello beautiful User !" + "\n" + "Welcome to VideoSweeper !" + ColorText.ANSI_RESET);
        System.out.println(ColorText.ANSI_YELLOW + "You have " + count + " new video files found !" + ColorText.ANSI_RESET);
        System.out.println(ColorText.ANSI_GREEN + "You have " + fileNotCompressed + " video files not compressed !" + ColorText.ANSI_RESET);
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
}
