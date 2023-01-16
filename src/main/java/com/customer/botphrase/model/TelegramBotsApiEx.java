package com.customer.botphrase.model;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBotsApiEx extends TelegramBotsApi {

    private Bot bot;

    public TelegramBotsApiEx(Bot bot) throws TelegramApiException {
        super(DefaultBotSession.class);
        this.bot = bot;
        init();
    }

    public TelegramBotsApi init() {
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return telegramBotsApi;
    }
}
