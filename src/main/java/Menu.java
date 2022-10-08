import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    public static SetMyCommands mainMenu(){
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/random", "Читать случайный"));
        commands.add(new BotCommand("/by_tags", "Получить по тегам"));
        commands.add(new BotCommand("/search", "Найти анекдот"));
        commands.add(new BotCommand("/help", "Справка"));

        SetMyCommands setMyCommands = SetMyCommands.builder().commands(commands).build();
        return setMyCommands;
    }

    public static ReplyKeyboardMarkup tags(){
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Про работу");
        row1.add("Про интернет");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Про негров");
        row2.add("Политика");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Про евреев");
        row3.add("Про блондинок");

        KeyboardRow row4 = new KeyboardRow();
        row4.add("Про Петьку И Василия Ивановича");
        row4.add("Про тещу");

        return
                ReplyKeyboardMarkup
                        .builder()
                        .selective(true)
                        .oneTimeKeyboard(true)
                        .keyboardRow(row1)
                        .keyboardRow(row2)
                        .keyboardRow(row3)
                        .keyboardRow(row4)
                        .selective(true)
                        .isPersistent(true)
                        .build();

    }


    public static InlineKeyboardMarkup ratingsMenu() {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder();

        List<InlineKeyboardButton> row1Buttons = new ArrayList<InlineKeyboardButton>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Очень смешно");
        inlineKeyboardButton.setCallbackData("1");
        row1Buttons.add(inlineKeyboardButton);
        inlineKeyboardButton = new InlineKeyboardButton("Смешно");
        inlineKeyboardButton.setCallbackData("2");
        row1Buttons.add(inlineKeyboardButton);

        List<InlineKeyboardButton> row2Buttons = new ArrayList<InlineKeyboardButton>();
        inlineKeyboardButton = new InlineKeyboardButton("Не смешно");
        inlineKeyboardButton.setCallbackData("3");
        row2Buttons.add(inlineKeyboardButton);
        inlineKeyboardButton = new InlineKeyboardButton("Совсем не смешно");
        inlineKeyboardButton.setCallbackData("4");
        row2Buttons.add(inlineKeyboardButton);

        List<InlineKeyboardButton> row3Buttons = new ArrayList<InlineKeyboardButton>();
        inlineKeyboardButton = new InlineKeyboardButton("Следующий");
        inlineKeyboardButton.setCallbackData("next");
        row3Buttons.add(inlineKeyboardButton);


        return inlineKeyboardMarkupBuilder
                .keyboardRow(row1Buttons)
                .keyboardRow(row2Buttons)
                .keyboardRow(row3Buttons)
                .build();
    }
}
