package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.Fraction;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

public class Compressor {

    public static void compress_mkv(String path) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/bin/ffmpeg"); // Change this path to your ffmpeg path
        FFprobe ffprobe = new FFprobe("/bin/ffprobe"); // Change this path to your ffprobe path
        
        File file = new File(path);
        File output = new File(path.substring(0, path.lastIndexOf(".")) + "-tmp" + path.substring(path.lastIndexOf(".")));

        FFmpegProbeResult probeResult = ffprobe.probe(file.getAbsolutePath());
        
        System.out.println(getVideoFrameRate(probeResult, file));
        System.out.println(getVideoWidth(probeResult, file));
        System.out.println(getVideoHeight(probeResult, file));

        FFmpegBuilder builder = new FFmpegBuilder()
            .setInput(file.getAbsolutePath())
            .overrideOutputFiles(true)
            .addOutput(output.getAbsolutePath())
            .setVideoCodec("libx264")
            .setVideoFrameRate(getVideoFrameRate(probeResult, file).floatValue())
            .setVideoResolution(getVideoWidth(probeResult, file), getVideoHeight(probeResult, file))
            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
            .done();

            FFmpegJob job = new FFmpegExecutor(ffmpeg, ffprobe).createJob(builder, new ProgressListener() {

                // Using the FFmpegProbeResult determine the duration of the input
                final double duration_ns = probeResult.getFormat().duration * TimeUnit.SECONDS.toNanos(1);
            
                @Override
                public void progress(Progress progress) {
                    double percentage = progress.out_time_ns / duration_ns;
            
                    // Print out interesting information about the progress
                    System.out.println(String.format(
                    "[%.0f%%] status:%s frame:%s time:%s ms fps:%.0f speed:%.2fx",
                    percentage * 100,
                    ColorText.ANSI_CYAN + progress.status + ColorText.ANSI_RESET,
                    ColorText.ANSI_YELLOW + progress.frame + ColorText.ANSI_RESET,
                    FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                    progress.fps.doubleValue(),
                    progress.speed
                ));

                }
            });
            
        job.run();

        if (file.length() <= output.length()) {
            System.out.println(ColorText.ANSI_RED + "Compressed file (" + file .getName() + ") is bigger than original file !" + ColorText.ANSI_RESET);
            output.delete();
        }
        else {
            System.out.println(ColorText.ANSI_GREEN + "Compressed " + file.getName() + " successfully !" + ColorText.ANSI_RESET);
            file.delete();
            output.renameTo(file);
        }
    }

    private static Fraction getVideoFrameRate(FFmpegProbeResult probeResult, File file) throws IOException {
        int i = 0;
        while (probeResult.getStreams().get(i).avg_frame_rate == null) {
            i++;
        }
        return probeResult.getStreams().get(i).avg_frame_rate;
    }

    private static int getVideoWidth(FFmpegProbeResult probeResult, File file) throws IOException {
        int i = 0;
        while (probeResult.getStreams().get(i).width == 0) {
            i++;
        }
        return probeResult.getStreams().get(i).width;
    }

    private static int getVideoHeight(FFmpegProbeResult probeResult, File file) throws IOException {
        int i = 0;
        while (probeResult.getStreams().get(i).height == 0) {
            i++;
        }
        return probeResult.getStreams().get(i).height;
    }
}
