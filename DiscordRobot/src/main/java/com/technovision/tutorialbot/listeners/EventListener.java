package com.technovision.tutorialbot.listeners;

import com.technovision.tutorialbot.LotterySystem.CityGodTemple;
import com.technovision.tutorialbot.LotterySystem.Tarot;
import com.technovision.tutorialbot.calculator.MatrixCalculator;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.Arrays;

public class EventListener extends ListenerAdapter {
    Stock data = new Stock("0050");
    MatrixCalculator calculator = new MatrixCalculator();
    CityGodTemple Sign = new CityGodTemple();
    Tarot tarot = new Tarot();

    public EventListener() throws IOException {
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String ChannelNumber = "1059013564649709618";
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();//讀取文字
        if (content.startsWith("!?")) {
            if (event.getChannel().getId().equals("1055344106580222043")) {
                MessageChannel channel = event.getChannel();
                data.setStockNumber(content);
                if (data.SearchStockMarket()) {
                    channel.sendMessage("Stock number: " + data.getStockNumber() + "\n"
                            + "Price: " + data.getPrice() + "\n"
                            + "Change%: " + data.getChange() + "\n"
                            + "Volume: " + data.getVolume()).queue();
                } else {
                    channel.sendMessage("No stock found").queue();
                }// Important to call .queue() on the RestAction returned by sendMessage(...)
            }

        }
        if (content.startsWith("SetMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                String[] arr = content.split(" ");//分割陣列

                if (arr.length < 3) {//先檢查是否有row and column
                    channel.sendMessage("format error.").queue();
                    return;
                }

                int row = Integer.parseInt(arr[1]);
                int column = Integer.parseInt(arr[2]);

                if (arr.length != 3 + row * column) {//檢查是否有慢法填滿陣列元素，避免報錯崩潰
                    channel.sendMessage("format error.").queue();
                    return;
                }

                int index = 3;
                calculator.setMatrix1(row, column);
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        calculator.matrix1[i][j] = Double.valueOf(arr[index++]);
                    }
                }
                channel.sendMessage("Matrix1 = ").queue();
                for (int i = 0; i < calculator.matrix1.length; i++) {
                    channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                }
                System.out.println("matrix1 set success ");
                calculator.isThereAMatrix1 = true;
            }
        }
        if (content.startsWith("SetMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                String[] arr = content.split(" ");
                if (arr.length < 3) {//先檢查是否有row and column
                    channel.sendMessage("format error.").queue();
                    return;
                }
                int row = Integer.parseInt(arr[1]);
                int column = Integer.parseInt(arr[2]);

                if (arr.length != 3 + row * column) {//檢查是否有慢法填滿陣列元素，避免報錯崩潰
                    channel.sendMessage("format error.").queue();
                    return;
                }
                int index = 3;
                calculator.setMatrix2(row, column);
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        calculator.matrix2[i][j] = Double.valueOf(arr[index++]);
                    }
                }
                channel.sendMessage("Matrix2 = ").queue();
                for (int i = 0; i < calculator.matrix2.length; i++) {
                    channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                }
                System.out.println("matrix2 set success ");
                calculator.isThereAMatrix2 = true;
            }
        }
        if (content.startsWith("MatrixMul")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix1 && calculator.isThereAMatrix2) {
                    if (calculator.matrix1[0].length == calculator.matrix2.length) {
                        calculator.MatrixMul();
                        for (int i = 0; i < calculator.matrix1.length; i++) {
                            channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                        }
                        channel.sendMessage("X").queue();
                        for (int i = 0; i < calculator.matrix2.length; i++) {
                            channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                        }
                        channel.sendMessage("=").queue();
                        for (int i = 0; i < calculator.matrix1.length; i++) {
                            channel.sendMessage(Arrays.toString(calculator.getMatrix3(i))).queue();
//                    System.out.println(Arrays.toString(calculator.getMatrix3(i)));
                        }
                    } else {
                        channel.sendMessage("無法進行矩陣運算").queue();
                        return;
                    }
                } else {
                    channel.sendMessage("無法進行矩陣運算").queue();
                    return;
                }
            }
        }
        if (content.startsWith("PutMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix3) {
                    calculator.PutMatrix1();
                    channel.sendMessage("Matrix1 = ").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                } else {
                    channel.sendMessage("無法放入Matrix1").queue();
                    return;
                }
            }
        }
        if (content.startsWith("PutMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix3) {
                    calculator.PutMatrix2();
                    channel.sendMessage("Matrix2 = ").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                } else {
                    channel.sendMessage("無法放入Matrix2").queue();
                    return;
                }
            }
        }
        if (content.startsWith("MatrixExchange")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix1 && calculator.isThereAMatrix2) {
                    calculator.MatrixExchange();
                    channel.sendMessage("Matrix1 = ").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("Matrix2 = ").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1 and matrix2, first.").queue();
                    return;
                }
            }
        }
        if (content.startsWith("TransportMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix1) {
                    calculator.TransportMatrix1();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        if (i == 0)
                            channel.sendMessage(Arrays.toString(calculator.matrix1[i]) + "T").queue();
                        else
                            channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("=").queue();
                    for (int i = 0; i < calculator.matrix3.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.getMatrix3(i))).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1, first").queue();
                    return;
                }
            }
        }
        if (content.startsWith("TransportMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix2) {
                    calculator.TransportMatrix2();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        if (i == 0)
                            channel.sendMessage(Arrays.toString(calculator.matrix2[i]) + "T").queue();
                        else
                            channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("=").queue();
                    for (int i = 0; i < calculator.matrix3.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.getMatrix3(i))).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1, first").queue();
                    return;
                }
            }
        }
        if (content.startsWith("DisplayMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix1) {
                    channel.sendMessage("Matrix1 = ").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1, first").queue();
                    return;
                }
            }
        }
        if (content.startsWith("DisplayMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix2) {
                    channel.sendMessage("Matrix2 = ").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix2, first").queue();
                    return;
                }
            }
        }
        if (content.startsWith("DeterminateMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                double det;
                if (calculator.matrix1.length != calculator.matrix1[0].length) {
                    channel.sendMessage("Is not square matrix.").queue();
                    return;
                }
                if (calculator.isThereAMatrix1) {
                    det = calculator.DeterminateMatrix1();
                } else {
                    channel.sendMessage("Please enter matrix1, first").queue();
                    return;
                }
                for (int i = 0; i < calculator.matrix1.length; i++) {
                    channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                }
                if (det == 0) {
                    channel.sendMessage("的行列式值 = 0").queue();
                } else if (Math.round(det) == det) {
                    channel.sendMessage("的行列式值 = " + Math.round(det)).queue();
                } else {
                    channel.sendMessage("的行列式值 = " + det).queue();
                }
            }
        }
        if (content.startsWith("DeterminateMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                double det;
                if (calculator.matrix2.length != calculator.matrix2[0].length) {
                    channel.sendMessage("Is not square matrix.").queue();
                    return;
                }
                if (calculator.isThereAMatrix2) {
                    det = calculator.DeterminateMatrix2();
                } else {
                    channel.sendMessage("Please enter matrix2, first").queue();
                    return;
                }
                for (int i = 0; i < calculator.matrix2.length; i++) {
                    channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                }
                if (Math.round(det) == det) {
                    det = Math.round(det);
                }
                if (det == 0) {
                    channel.sendMessage("的行列式值 = 0").queue();
                } else if (Math.round(det) == det) {
                    channel.sendMessage("的行列式值 = " + Math.round(det)).queue();
                } else {
                    channel.sendMessage("的行列式值 = " + det).queue();
                }
//                System.out.println(det);
            }
        }
        if (content.startsWith("InverseMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.matrix1.length != calculator.matrix1[0].length) {
                    channel.sendMessage("Is not square matrix.").queue();
                    return;
                }
                if (calculator.isThereAMatrix1) {
                    if (calculator.DeterminateMatrix1() == 0) {
                        channel.sendMessage("Matrix1 no inverse").queue();
                        return;
                    }
                    calculator.InverseMatrix1();
                    calculator.isThereAMatrix3 = true;
                    for (int i = 0; i < calculator.matrix3.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix3[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1, first").queue();
                }
//        channel.sendMessage().queue();
            }
        }
        if (content.startsWith("InverseMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.matrix2.length != calculator.matrix2[0].length) {
                    channel.sendMessage("Is not square matrix.").queue();
                    return;
                }
                if (calculator.isThereAMatrix2) {
                    if (calculator.DeterminateMatrix2() == 0) {
                        channel.sendMessage("Matrix2 no inverse").queue();
                        return;
                    }
                    calculator.InverseMatrix2();
                    calculator.isThereAMatrix3 = true;
                    for (int i = 0; i < calculator.matrix3.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix3[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix2, first").queue();
                }

            }
        }
        if (content.startsWith("MatrixAdd")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.matrix1.length != calculator.matrix2.length || calculator.matrix1[0].length != calculator.matrix2[0].length) {
                    channel.sendMessage("format error").queue();
                    return;
                }
                if (calculator.isThereAMatrix1 && calculator.isThereAMatrix2) {
                    calculator.MatrixAdd();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("+").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                    channel.sendMessage("=").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.getMatrix3(i))).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1 or matrix2").queue();
                }
            }
        }
        if (content.startsWith("MatrixMinus")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.matrix1.length != calculator.matrix2.length || calculator.matrix1[0].length != calculator.matrix2[0].length) {
                    channel.sendMessage("format error").queue();
                    return;
                }
                if (calculator.isThereAMatrix1 && calculator.isThereAMatrix2) {
                    calculator.MatrixMinus();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("-").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                    channel.sendMessage("=").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.getMatrix3(i))).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1 or matrix2").queue();
                }
            }
        }
        if (content.startsWith("ScalarMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                String[] arr = content.split(" ");
                if (arr.length != 2) {
                    channel.sendMessage("format error.").queue();
                    return;
                }

                if (calculator.isThereAMatrix1) {
                    calculator.ScalarMatrix1(Double.parseDouble(arr[1]));
                    channel.sendMessage("Matrix1 X " + arr[1] + " = ").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1, first.").queue();
                }
            }
        }
        if (content.startsWith("ScalarMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                String[] arr = content.split(" ");
                if (arr.length != 2) {
                    channel.sendMessage("format error.").queue();
                    return;
                }

                if (calculator.isThereAMatrix2) {
                    calculator.ScalarMatrix2(Double.parseDouble(arr[1]));
                    channel.sendMessage("Matrix2 X " + arr[1] + " = ").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix2, first.").queue();
                }
            }
        }
        if (content.startsWith("ReduceRowEchelonMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix1) {
                    calculator.ReduceRowEchelonMatrix1();
                    calculator.isThereAMatrix3 = true;
                    channel.sendMessage("Matrix1 = ").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("Its reduced row echelon form is").queue();
                    for (int i = 0; i < calculator.matrix3.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix3[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix1, first.").queue();
                }
            }
        }
        if (content.startsWith("ReduceRowEchelonMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix2) {
                    calculator.ReduceRowEchelonMatrix2();
                    calculator.isThereAMatrix3 = true;
                    channel.sendMessage("Matrix2 = ").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                    channel.sendMessage("Its reduced row echelon form is").queue();
                    for (int i = 0; i < calculator.matrix3.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix3[i])).queue();
                    }
                } else {
                    channel.sendMessage("Please enter matrix2, first.").queue();
                }
            }
        }
        if (content.startsWith("RankOfMatrix1")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix1) {
                    channel.sendMessage("Matrix1 = ").queue();
                    for (int i = 0; i < calculator.matrix1.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix1[i])).queue();
                    }
                    channel.sendMessage("Its rank is " + calculator.RankOfMatrix1()).queue();
                } else {
                    channel.sendMessage("Please enter matrix1, first.").queue();
                }
            }
        }
        if (content.startsWith("RankOfMatrix2")) {
            if (event.getChannel().getId().equals(ChannelNumber)) {
                MessageChannel channel = event.getChannel();
                if (calculator.isThereAMatrix2) {
                    channel.sendMessage("Matrix2 = ").queue();
                    for (int i = 0; i < calculator.matrix2.length; i++) {
                        channel.sendMessage(Arrays.toString(calculator.matrix2[i])).queue();
                    }
                    channel.sendMessage("Its rank is " + calculator.RankOfMatrix2()).queue();
                } else {
                    channel.sendMessage("Please enter matrix2, first.").queue();
                }
            }
        }
        if (content.startsWith("drawLots")) {
            if (event.getChannel().getId().equals("1059746092436168724")) {
                MessageChannel channel = event.getChannel();
                try {
                    Sign.drawSign();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                channel.sendMessage(Sign.SignPicture()).queue();
                channel.sendMessage("籤詩釋解:").queue();
                channel.sendMessage(Sign.SignContent).queue();
            }
        }
        if (content.startsWith("drawTarot")) {
            if (event.getChannel().getId().equals("1059746092436168724")) {
                MessageChannel channel = event.getChannel();
                tarot.divination();
                channel.sendMessage(tarot.getCardName()+" ("+tarot.getProsAndCons()+")").queue();
                channel.sendMessage("https://tarothall.com"+tarot.getCardPicture()).queue();
                channel.sendMessage(tarot.getContent()).queue();
            }
        }
    }
}
