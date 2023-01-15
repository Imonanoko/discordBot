package com.technovision.tutorialbot.LotterySystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CityGodTemple {
    public int sign;
    public String SignContent = "";

    public void drawSign() throws IOException {
        sign = (int) (Math.random() * 59) + 1;
        URL url = new URL("http://www.citygod.tw/fortune.php?ans=" + sign);
        URLConnection uC = url.openConnection();
        InputStream stream = uC.getInputStream();
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(isr);
        String str;
        while ((str = br.readLine()) != null) {
            if (str.contains("line-height:20px")) {
                SignContent = str;
            }
        }
        SignContent=SignContent.replace("<p id=\"exp\" style=\"line-height:20px; \">","");
        SignContent=SignContent.replace("<","");
        SignContent=SignContent.replace(">","");
        SignContent=SignContent.replace("BR","\n");
        SignContent=SignContent.replace("/p","");

    }

    public String SignPicture() {
        return "http://www.citygod.tw/images/fortune/" + sign + ".png";
    }
}
