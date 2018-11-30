package com.lcf.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

    private String ffmpegEXE;
    public MergeVideoMp3(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }
    public void convertor(String videoInputPath,String mp3InputPath,double seconds,String videoOutputPath) throws Exception{
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(mp3InputPath);
        command.add("-t");
        command.add(String.valueOf(seconds));
        command.add("-y");
        command.add(videoOutputPath);
        ProcessBuilder builder= new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();

       // process.start();
    }
    public static void main(String[] args){
        MergeVideoMp3 ffmpeg=new MergeVideoMp3("C:\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            ffmpeg.convertor("C:\\ffmpeg\\bin\\text.mp4","C:\\ffmpeg\\bin\\testmp3.mp3",6,"C:\\ffmpeg\\bin\\new11.avi");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
