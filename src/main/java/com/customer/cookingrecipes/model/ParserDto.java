package com.customer.cookingrecipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.Column;
import java.io.IOException;

@Data
@NoArgsConstructor
public class ParserDto {

    void parser(String strURL) {
        String className = "su-note-inner su-u-clearfix su-u-trim";
        Document doc = null;
        try {

            doc = Jsoup.connect(strURL).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert doc != null; //
        Elements elQuote = doc.getElementsByClass(className);

        elQuote.forEach(el -> {
            //todo  private ArrayList<String> quoteList; class Storage
//            quoteList.add(el.text());
        });
    }
}
