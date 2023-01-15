package com.technovision.tutorialbot.listeners;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stock {
    private String price;
    private String change;
    private String volume;
    private static final Pattern p = Pattern.compile("[^0-9.%+-]");
    private String stockNumber;

    public Stock(String stockNumber){
        this.stockNumber = stockNumber;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public boolean SearchStockMarket(){
        int time = 0;
        try {
            Matcher m = p.matcher(stockNumber);
            stockNumber = m.replaceAll("").trim();
            URL url = new URL("https://histock.tw/stock/"+this.stockNumber);
            URLConnection uC = url.openConnection();
            InputStream stream = uC.getInputStream();
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(isr);
            String str;
            while ((str = br.readLine()) != null) {
                if ( str.contains("Price1_lbTPrice")) {
                    time++;
                    m = p.matcher(str);
                    price = m.replaceAll("").trim();
                    price = price.substring(2);
                    System.out.println("price: "+price);
                }
                if (str.contains("Price1_lbTPercent")) {
                    time++;
                    m = p.matcher(str);
                    change = m.replaceAll("").trim();
                    change = change.substring(5);
                    System.out.println("change: "+change);
                }
                if (str.contains("Price1_lbTVolume")) {
                    time++;
                    m = p.matcher(str);
                    volume = m.replaceAll("").trim();
                    volume = volume.substring(3);
                    System.out.println("volume: "+volume);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time==4;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }
    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }
    public String getVolume(){
        return volume;
    }
    }




