package com.customer.cookingrecipes.model;

import com.customer.cookingrecipes.repository.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@Component
public class Bot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);
    private static final String BOT_TOKEN = "5342429230:AAGlSP37Q8UxFN57Z02cHO2phgmBsU-KlGA"; //токен выдан телегой
    private static final String BOT_NAME = "Expressions"; //имя бота

    private final Storage storage;

    public Bot(Storage storage) {
        this.storage = storage;
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
        } else if (textMsg.equals("/get") || textMsg.equals("Просвяти")) { //туду
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