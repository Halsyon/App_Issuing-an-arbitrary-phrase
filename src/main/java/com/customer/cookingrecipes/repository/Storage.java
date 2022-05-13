package com.customer.cookingrecipes.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    private ArrayList<String> quoteList;

    @PostConstruct
    private void init() {
        String three = "Если у тебя есть человек, которому можно рассказать сны,"
                + " ты не имеешь права считать себя одиноким...\n\nФаина Раневская";
        quoteList = new ArrayList<>();
        quoteList.add("Не бывает безвыходных ситуаций. Бывают ситуации, выход из которых тебя не устраивает. \n\nНаруто.");
        quoteList.add("Пройдите мимо нас и простите нам наше счастье.\n\nМихайлович Достоевский");
        quoteList.add(three);
    }

    public String getRandQuote() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}
