package com.customer.cookingrecipes;

import com.customer.cookingrecipes.model.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class CookingrecipesApplication {

    @Bean
   public TelegramBotsApi telegramBotsApiInit() {
        TelegramBotsApi telegramBotsApi = null;
        try {
           telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return telegramBotsApi;
    }

    public static void main(String[] args) {
        SpringApplication.run(CookingrecipesApplication.class, args);
    }

}
