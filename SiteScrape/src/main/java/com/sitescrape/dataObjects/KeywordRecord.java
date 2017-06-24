package com.sitescrape.dataObjects;


/**
 * Created by Deslyxia on 6/23/17.
 */


import java.sql.Timestamp;

/**
 * Represents one row from tblKeywords
 *
 * url: This is the url where the keyword was captured
 * keyword: Captured keyword
 * count: How many times the keyword was found on a given url
 * updated: Timestamp of the scrape that captured this data
 */
public class KeywordRecord {
    private String url;
    private String keyword;
    private Integer count;
    private Timestamp updated;

    public KeywordRecord(String url, String keyword, Integer count, Timestamp updated) {
        this.url = url;
        this.keyword = keyword;
        this.count = count;
        this.updated = updated;
    }

    public String getUrl() {
        return url;
    }
    public String getKeyword() {
        return keyword;
    }
    public Integer getCount() {
        return count;
    }
    public Timestamp getUpdated() {
        return updated;
    }
}
