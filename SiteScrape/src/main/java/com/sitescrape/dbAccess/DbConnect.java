package com.sitescrape.dbAccess;

import com.sitescrape.dataObjects.KeywordRecord;
import com.sitescrape.util.RuntimeData;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Deslyxia on 6/23/17.
 */
public class DbConnect {

    private static DbConnect instance;

    private String driver;
    private String host;
    private String uname;
    private String pwd;

    private Connection con;

    public static synchronized DbConnect getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DbConnect();
        }
        return instance;
    }

    //Explicit private constructor
    private DbConnect() throws SQLException, ClassNotFoundException {
        driver = RuntimeData.INSTANCE.getConfigurationValue("persistence.driver");
        host = RuntimeData.INSTANCE.getConfigurationValue("persistence.url");
        uname= RuntimeData.INSTANCE.getConfigurationValue("persistence.user");
        pwd = RuntimeData.INSTANCE.getConfigurationValue("persistence.password");

        Class.forName(driver);
        con = DriverManager.getConnection(host,uname,pwd);
        con.setAutoCommit(false); //Commit transaction manually
    }

    //Public method to write Keyword Records to DB
    public void writeUrlKeywords (ArrayList<KeywordRecord> recordSet) throws SQLException {
        String sql = "INSERT INTO tblKeywords (url, keyword, count, updated) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        for (KeywordRecord record : recordSet) {
            ps.setString(1, record.getUrl());
            ps.setString(2, record.getKeyword());
            ps.setInt(3, record.getCount());
            ps.setTimestamp(4, record.getUpdated());
            ps.addBatch();
        }
        ps.executeBatch();
        con.commit();
    }

    //Public method to remove existing Keyword Records from DB
    public void removeUrlRecords(String url) throws SQLException {
        String sql = "DELETE FROM tblKeywords WHERE url = \'" +  url + "\'" ;
        PreparedStatement ps = con.prepareStatement(sql);
        System.out.println("SQL : " + sql);
        ps.executeUpdate(sql);
        con.commit();
    }
}
