package com.technovision.tutorialbot.LotterySystem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Tarot {
    private final String[] arr = {"正位", "逆位"};
    private String content;
    private String ProsAndCons;
    private String CardName;
    private String CardPicture;

    public void divination() {
        int card = (int) (Math.random() * 78);
        ProsAndCons = arr[(int) (Math.random() * 2)];
        content = "";
        CardName = "";
        CardPicture = "";
        try {
            URL url = new URL("https://tarothall.com/card/" + card);
            URLConnection uC = url.openConnection();
            InputStream stream = uC.getInputStream();
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(isr);
            String str;
            boolean NameCanWrite = false;
            boolean ContentCanWrite = false;
            while ((str = br.readLine()) != null) {
                if (str.contains("<img")) {
                    NameCanWrite = true;
                }
                if (NameCanWrite) {
                    CardName += str;
                }
                if (str.contains("data-holder-rendered")) {
                    NameCanWrite = false;
                }
                if (str.contains("<p class=\"card-text mb-auto\">")) {
                    ContentCanWrite = true;
                }
                if (ContentCanWrite) {
                    content += str;
                }
                if (str.contains("/p")) {
                    ContentCanWrite = false;
                }

            }
            CardName = CardName.replace("<img class=\"mx-auto flex-auto d-block d-md-none img-fluid\" alt=", "").trim();
            CardName = CardName.replace(" ", "");
            content = content.replace("<p class=\"card-text mb-auto\">", "");
            content = content.replace("</p>", "").trim();
            content = content.replace("<br/>", "\n");
            char[] DealString = CardName.toCharArray();
            CardName="";
            int DoubleDot = 0;
            for (int i = 0; i < DealString.length; i++) {
                if (DealString[i] == '\"') {
                    DoubleDot++;
                } else if (DoubleDot == 1) {
                    CardName += DealString[i];
                } else if (DoubleDot == 3) {
                    CardPicture += DealString[i];
                }
                else if(DoubleDot > 3){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContent() {
        return content;
    }

    public String getProsAndCons() {
        return ProsAndCons;
    }

    public String getCardName() {
        return CardName;
    }

    public String getCardPicture() {
        return CardPicture;
    }
}
