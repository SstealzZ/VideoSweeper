package fr.sstealzz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.sstealzz.data.Data;
import fr.sstealzz.data.FileExplorer;
import fr.sstealzz.data.Json;
import fr.sstealzz.utilities.Menu;

public class App 
{
    public static void main( String[] args ) throws IOException {
        Json json = new Json("config.json");
        json.init();
        FileExplorer fileExplorer = new FileExplorer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to scan new file ? (y/n)");
        String answer = sc.nextLine();
        List<File> files = new ArrayList<>();
        if (answer.equals("y")) {
            files = fileExplorer.findVideoFiles();
        }
        List<Data> datas = json.getData();
        Menu init = new Menu();
        init.Init(files, datas, fileExplorer);
        // Test test = new Test();
        // test.testJson("config.json");
        // compressor.compress_mkv("test");
    }
}