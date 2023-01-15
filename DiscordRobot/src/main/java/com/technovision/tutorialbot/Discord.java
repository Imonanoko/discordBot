package com.technovision.tutorialbot;

import com.technovision.tutorialbot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Discord {
    private final Dotenv config;

    public Discord() throws LoginException, IOException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        JDA builder = JDABuilder.createDefault(token).build();
        builder.addEventListener(new EventListener());
    }

    public Dotenv getConfig() {
        return config;
    }


    public static void main(String[] args) {
        try {
            Discord bot = new Discord();
        }catch(LoginException e){
            System.out.print("Error: Provided bot token is invalid!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
