package com.lcf.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FFMpegTest {

    private String ffmpegEXE;
    public FFMpegTest(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }
    public void convertor(String videoInputPath,String videoOutputPath) throws Exception{
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutputPath);
        ProcessBuilder builder= new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();

       // process.start();
    }
    public static void main(String[] args){
        FFMpegTest ffmpeg=new FFMpegTest("C:\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            ffmpeg.convertor("C:\\ffmpeg\\bin\\text.mp4","C:\\ffmpeg\\bin\\bean.avi");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
