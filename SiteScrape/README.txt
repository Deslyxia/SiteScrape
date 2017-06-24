This project will accept either a single URL or comma separated list of URLs from the command line.
It will then spin up a thread for each URL and do the following tasks:

1) Connect to the URL
2) Scrape the screen
3) Remove all encoding and strip out punctuation and extra whitespace
4) Create a master list of words found on the page and a count for each
5) Connect to backend DB (In this case MySql)
6) Remove any previous keywords for the URL
7) Populate tblKeywords with the new scrape results




Command Line parameters

    Required

    -u   "URL"  Single URL or , delimited list of URLS to scrape and catalog

    -c   "config"   Directory containing config files



Run Command (From project root dir)

    java -cp  release/artifacts/SiteScrape_jar/SiteScrape.jar com.sitescrape UrlProcessor -c {Path to Config} -u www.yahoo.com



Config File Instructions

    Included in the project in the /conf folder is "scraper-config.xml.tpl". Prior to running this file will need
    to be edited. Specifically the url, username, pwd to connect to your mySql instance of choice will need to be provided.


Database Instructions

    In the mySql server you will need to create a database called SCRAPE and add tblKeywords
    and give your user READ/WRITE/DELETE permissions:

        CREATE DATABASE SCRAPE;
        use SCRAPE;

        CREATE TABLE tblKeywords (
        id int NOT NULL AUTO_INCREMENT,
        url varchar(255) NOT NULL,
        keyword varchar(255) NOT NULL,
        count int NOT NULL,     
        updated TIMESTAMP NOT NULL,
        PRIMARY KEY (id)
        );


