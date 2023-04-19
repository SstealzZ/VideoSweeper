package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class Compressor {

    public void compress_mkv(String path) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/bin/ffprobe");
        
        File file = new File(path + ".mkv");
        File output = new File(path + "compressed" + ".mkv");

        FFmpegBuilder builder = new FFmpegBuilder()
            .setInput(file.getAbsolutePath())
            .overrideOutputFiles(true)
            .addOutput(output.getAbsolutePath())
            .setVideoCodec("libx264")
            .setVideoFrameRate(30, 1)
            .setVideoResolution(1920, 1080)
            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
            .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
        System.out.println("Compressed " + file.getName());
    }
}