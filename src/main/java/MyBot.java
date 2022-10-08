import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "5048010637:AAHgzYsTADTV2LA8Ip4TZUvw7FALTu3GdZs";
    private static final String BOT_USERNAME = "YOUR_BOT_USERNAME";
    private static String lastCommand = "";
    private static final List tags = new ArrayList<String>();

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onRegister() {
        try {
            execute(Menu.mainMenu());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            lastCommand = messageText;
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));

            switch (messageText) {
                case "/random":
                    message.setText("Текст случайного анекдота");
                    message.setReplyMarkup(Menu.ratingsMenu());
                    break;
                case "/by_tags":
                    message.setText("Введите тему анекдота");
                    message.setReplyMarkup(Menu.tags());
                    break;
                case "/search":
                    message.setText("Введите текст анекдота(как в гугле)");
                    break;
                case "/help":
                    message.setText("List of commands:\n/start - Start the bot\n/help - Get help\n/commands - Show list of commands");
                    break;
                default:
                    message.setText("Unknown command");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else if(update.hasCallbackQuery()){
            String callData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if(callData.equals("next")){
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText("Текст случайного анекдота");
                message.setReplyMarkup(Menu.ratingsMenu());
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //TODO
                //выставляем оценку
            }

        }
    }
}