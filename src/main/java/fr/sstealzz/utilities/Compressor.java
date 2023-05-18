package fr.sstealzz.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

public class Compressor {

    public static void compress_mkv(String path) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/bin/ffprobe");
        
        File file = new File(path);
        File output = new File(path.substring(0, path.lastIndexOf(".")) + "-tmp" + path.substring(path.lastIndexOf(".")));

        FFmpegProbeResult probeResult = ffprobe.probe(file.getAbsolutePath());
        FFmpegStream stream = probeResult.getStreams().get(0);

        FFmpegBuilder builder = new FFmpegBuilder()
            .setInput(file.getAbsolutePath())
            .overrideOutputFiles(true)
            .addOutput(output.getAbsolutePath())
            .setVideoCodec("libx264")
            .setVideoFrameRate(stream.avg_frame_rate.floatValue())
            .setVideoResolution(stream.width, stream.height)
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

        System.out.println(ColorText.ANSI_GREEN + "Compressed " + file.getName() + " successfully !" + ColorText.ANSI_RESET);
        file.delete();
        output.renameTo(file);
    }
}
