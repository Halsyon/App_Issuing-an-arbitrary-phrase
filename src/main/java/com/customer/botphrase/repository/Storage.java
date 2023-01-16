package com.customer.botphrase.repository;

import com.customer.botphrase.model.Expression;
import com.customer.botphrase.utils.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Storage {

    private static final Logger LOGGER = LoggerFactory.getLogger(Storage.class);
    private List<String> lists;
    private List<Expression> expressions;

    @PostConstruct
    private void init() throws IOException {
        LOGGER.info("INIT!");
        lists = new ArrayList<>();
        expressions = new ArrayList<>();
        parser(AppConstant.URL_MAIN);
        LOGGER.info("Размер хранилища после парсинга {}", expressions.size());
    }

    /**
     * получаем произвольное значение в интервале от 0 до самого большого индекса
     *
     * @return Цитата и автор
     */
    public String getRandQuote() {
        LOGGER.info("Размер хранилища getRandQuote {}", expressions.size());
        int randValue = (int) (Math.random() * expressions.size());
        var exp = expressions.get(randValue);
        return exp.getCitation() + "  " + exp.getAuthor();
    }

    /**
     * Парсинг указанного сайта html страницы и всех остальных
     *
     * @param url главная/титульная страница
     * @throws IOException
     */
    void parser(String url) throws IOException {
        LOGGER.info("Start!");
        int count = 0;

        lists.add(url);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();

        /*Pagination-создаем список страниц для парсинга*/
        var rsl2 = doc.select("div.pagination");
        var rsl3 = rsl2.select("li.pager-item");
        for (Element element : rsl3.select("a")) {
            var str = element.toString().split("href=\"");
            var str1 = str[1].split("\">");
            lists.add(str1[0]);
        }
        /*парсим*/
        for (int i = 0; i < lists.size() - 1; i++) {
            Document doc1 = Jsoup.connect(lists.get(2))
                    .userAgent("Mozilla/5.0 Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();

            for (Element rts : doc1.select("div.view-content")) {
                for (Element rsll : rts.select("div.node__content")) {
                    Expression result = new Expression();
                    var elm = rsll.select(AppConstant.CSS_QUERY1);
                    result.setCitation(elm.text());

                    var element = rsll.select(AppConstant.CSS_QUERY2);  //автор
                    if (element.hasText()) {
                        System.out.println("____--- " + element.text());
                        result.setAuthor(element.text());
                    }
                    var select = rsll.select(AppConstant.CSS_QUERY3);
                    if (select.hasText()) {
                        System.out.println("____=== " + select.text());
                        result.setAuthor(select.text());
                    }
                    var element1 = rsll.select(AppConstant.CSS_QUERY4); //автор кино
                    if (element1.hasText()) {
                        System.out.println("____+++ " + element1.text());
                        result.setAuthor(element1.text());
                    }

                    var elements = rsll.select(AppConstant.CSS_QUERY5); //автор кино
                    if (elements.hasText()) {
                        System.out.println("____000 " + elements.text());
                        result.setAuthor(elements.text());
                    }

                    result.setId(++count);
                    expressions.add(result);
                }
            }
        }
        LOGGER.info("Ok!");
    }
}
