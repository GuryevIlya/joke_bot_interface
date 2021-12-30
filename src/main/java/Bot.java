import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    long chatId;
    String lastMessage = "";

    public ReplyKeyboardMarkup menu(String msg){
        ReplyKeyboardMarkup result = new ReplyKeyboardMarkup();

        result.setSelective(true);
        result.setResizeKeyboard(true);
        result.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> rows = new ArrayList<KeyboardRow>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("Добавить опрос");
        keyboardFirstRow.add("Мои опросы");
        rows.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("Рейтинг");
        keyboardSecondRow.add("Оценивать");
        rows.add(keyboardSecondRow);

        result.setKeyboard(rows);

        return result;
    }

    public InlineKeyboardMarkup inlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Удалить");
        inlineKeyboardButton1.setCallbackData("Button \"Удалить\" has been pressed");
        inlineKeyboardButton2.setText("Редактировать");
        inlineKeyboardButton2.setCallbackData("Button \"Редактировать\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        //rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private List<SendMediaGroup> myPoolsReply() throws IOException {
        List<SendMediaGroup> result = new ArrayList<>();
        for(File pool : new File("C:\\bot_media\\my_pools\\").listFiles()){
            SendMediaGroup mg = new SendMediaGroup();
            List<InputMedia> im = new ArrayList<InputMedia>();
            boolean isFirst = true;
            for(File photo : pool.listFiles()){
                if(photo.getName().contains("tag")){
                    continue;
                }
                InputMediaPhoto mp = new InputMediaPhoto();
                mp.setMedia(photo, photo.getName());
                StringBuffer caption =  new StringBuffer();
                if(isFirst){
                    for(String tag: FileUtils.readLines(new File(pool, "tags.txt"), "utf-8")){
                        caption.append("#").append(tag).append(" ");
                    }
                    mp.setCaption(caption.toString().trim());
                    mp.setParseMode(ParseMode.HTML);
                    isFirst = false;
                }
                im.add(mp);
            }
            mg.setMedia(im);
            mg.setChatId(chatId);
            result.add(mg);
        }

        return result;
    }

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        System.out.println(update.toString());
        chatId = update.getMessage().getChatId();


        if(update.getMessage().getText().equals("Мои опросы")) {

            try {
                for(SendMediaGroup sm : myPoolsReply()){
                    try {
                        execute(sm);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try{
                        SendMessage sendMessage1 = new SendMessage()
                                .setText("Загружено 22 января, 100 человек проголосовало, осталось показов 3")
                                .setChatId(chatId);
                        sendMessage1.setReplyMarkup(inlineKeyboardMarkup());
                        execute(sendMessage1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*for (int i = 0; i < 20; i++) {
                // sendMessage.setReplyMarkup(inlineKeyboardMarkup());

                    String path = "C:\\Users\\Илья\\Pictures\\zzW4Dv6x9SY.jpg";
                    FileInputStream file = new FileInputStream(new File(path));

                    String path1 = "C:\\Users\\Илья\\Pictures\\4931546_1dfc23.jpg";
                    FileInputStream file1 = new FileInputStream(new File(path));
                    SendMediaGroup mg = new SendMediaGroup();
                    List<InputMedia> im = new ArrayList<InputMedia>();

                    InputMediaPhoto mp = new InputMediaPhoto();
                    mp.setMedia(file, "kjj");
                    mp.setCaption("some caption <b>bold</b>");
                    mp.setParseMode(ParseMode.HTML);
                    im.add(mp);

                    InputMediaPhoto mp1 = new InputMediaPhoto();
                    mp1.setMedia(file1, "kjjггг");
                    mp1.setCaption("some caption <b>bold</b>");
                    mp1.setParseMode(ParseMode.HTML);
                    im.add(mp1);

                    mg.setReplyToMessageId(314);
                    mg.setMedia(im);
                    mg.setChatId(chatId);
                    execute(mg);*/

                 /*sendMessage.setReplyMarkup(menu(""));


                        String path = "C:\\Users\\Илья\\Pictures\\zzW4Dv6x9SY.jpg";
                        FileInputStream file = new FileInputStream( new File(path));

                        String path1 = "C:\\Users\\Илья\\Pictures\\4931546_1dfc23.jpg";
                        FileInputStream file1 = new FileInputStream( new File(path));


                        SendPhoto message = new SendPhoto().setPhoto("SomeText", file);
                        message.setChatId(chatId);
                        message.setCaption("etete vxvxcv fsdfsfd");
                        message.setReplyMarkup(inlineKeyboardMarkup());
                        execute(message);


                        SendMediaGroup mg = new SendMediaGroup();
                        List<InputMedia> im = new ArrayList<InputMedia>();

                        InputMediaPhoto mp = new InputMediaPhoto();
                        mp.setMedia(file, "kjj");
                        mp.setCaption("some caption <b>bold</b>");
                        mp.setParseMode(ParseMode.HTML);
                        im.add(mp);

                        InputMediaPhoto mp1 = new InputMediaPhoto();
                        mp1.setMedia(file1, "kjjггг");
                        mp1.setCaption("some caption <b>bold</b>");
                        mp1.setParseMode(ParseMode.HTML);
                        im.add(mp1);

                        mg.setReplyToMessageId(314);
                        mg.setMedia(im);
                        mg.setChatId(chatId);
                        execute(mg);*/


               /* try {
                    SendMessage sendMessage1 = new SendMessage()
                            .setChatId(chatId)
                            .setText("kk " + 1);
                    sendMessage1.setReplyMarkup(inlineKeyboardMarkup());
                    execute(sendMessage1);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }

        //    System.out.println(update.getMessage().getText() + " " + update.getMessage().getMessageId());

    public String getBotUsername() {
        return "IlyaTestSuuuperBot";
    }

    public String getBotToken() {
        return "5048010637:AAHgzYsTADTV2LA8Ip4TZUvw7FALTu3GdZs";
    }
}