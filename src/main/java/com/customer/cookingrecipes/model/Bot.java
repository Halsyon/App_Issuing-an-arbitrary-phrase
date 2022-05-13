package com.customer.cookingrecipes.model;

import com.customer.cookingrecipes.repository.Storage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "todo";
    private static final String BOT_NAME = "*todo";

    private final Storage storage;

    public Bot() {
        this.storage = new Storage();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {

                Message inMess = update.getMessage();

                String chatId = inMess.getChatId().toString();

                String response = parseMessage(inMess.getText());

                SendMessage outMess = new SendMessage();

                outMess.setChatId(chatId);
                outMess.setText(response);

                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String parseMessage(String textMsg) {
        String response;
        if (textMsg.equals("/start")) {
            response = "Приветствую, бот знает много цитат. Жми /get, чтобы получить случайную из них";
        } else if (textMsg.equals("/get")) {
            response = storage.getRandQuote();
        } else {
            response = "Сообщение не распознано";
        }
        return response;
    }

    @PostConstruct
    void initKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);

        keyboardRow.add(new KeyboardButton("Просвяти"));

        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

}