package com.technovision.tutorialbot.audioEventAdapter;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.demo.jda.TrackScheduler;

import java.util.Scanner;

public class GuildMusicManger {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    public final AudioPlayerSendHandler sendHandler;

    public GuildMusicManger(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return this.sendHandler;
    }
}
