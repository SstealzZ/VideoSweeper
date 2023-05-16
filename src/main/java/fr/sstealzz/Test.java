package fr.sstealzz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.sstealzz.data.FileExplorer;
import fr.sstealzz.data.Json;

public class Test {
    public void testJson(String path) throws IOException{
        // path = "testplexconfig.json"; // this is for testing
        Json json = new Json(path);
        json.init();
        FileExplorer fileExplorer = new FileExplorer();
        List <File> files = fileExplorer.findVideoFiles();
        Integer count = fileExplorer.getCount();
        // Data data = json.getDataByName("Akame Ga Kill 01.mp4");

        // System.out.println(data.getSizeFileInGo());
        // System.out.println(data.isCompressed());
        System.out.println(files);
        System.out.println(count);
    }
}
