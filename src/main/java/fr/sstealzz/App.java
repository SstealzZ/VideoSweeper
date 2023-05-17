package fr.sstealzz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import fr.sstealzz.data.Data;
import fr.sstealzz.data.FileExplorer;
import fr.sstealzz.data.Json;
import fr.sstealzz.utilities.Compressor;
import fr.sstealzz.utilities.Menu;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Json json = new Json("config.json");
        json.init();
        FileExplorer fileExplorer = new FileExplorer();
        List<File> files = fileExplorer.findVideoFiles();
        List<Data> datas = json.getData();
        Compressor compressor = new Compressor();
        Menu init = new Menu();
        init.Init(files, datas, fileExplorer);
        // Test test = new Test();
        // test.testJson("config.json");
        // compressor.compress_mkv("test");
    }
}