package fr.sstealzz;

import java.io.IOException;

import fr.sstealzz.utilities.Compressor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Compressor compressor = new Compressor();
        compressor.compress_mkv("test");
    }
}
