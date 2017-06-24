package com.sitescrape.processors;


import com.sitescrape.dataObjects.KeywordRecord;
import com.sitescrape.dbAccess.DbConnect;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Deslyxia on 6/22/17.
 */
public class UrlSiteScrape {

    public void siteScrape (String url) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Start Processing : " + url);
        Timestamp timeStamp = new java.sql.Timestamp(System.currentTimeMillis());
        ArrayList<KeywordRecord> krList = new ArrayList<>();

        //Scrape url
        Map<String,Integer> keywordMap = scrapeUrl(url);

        //Remove Old Records
        DbConnect.getInstance().removeUrlRecords(url);

        //Write new Records
        for (String kw : keywordMap.keySet()) {
            KeywordRecord kr = new KeywordRecord(url, kw, keywordMap.get(kw), timeStamp);
            krList.add(kr);
        }
        DbConnect.getInstance().writeUrlKeywords(krList);

    }

    private Map<String,Integer> scrapeUrl(String url) throws IOException  {
        Map<String,Integer> keywordMap= new HashMap<>();

        Document document = Jsoup.connect("http://" +url).get();
        String text = document.body().text();

        //Remove all non alphanumeric
        text = text.replace("[^A-Za-z0-9 ]" , "");

        //Split string on spaces
        String[] splitStrings = text.split("\\s+");

        for (String s : splitStrings) {
            if (keywordMap.containsKey(s.trim())) {
                //Update map with incremented count
                keywordMap.put(s,keywordMap.get(s.trim()) + 1);
            } else {
                //Create new entry in keyword map
                keywordMap.put(s,1);
            }
        }

        return keywordMap;
    }

}
