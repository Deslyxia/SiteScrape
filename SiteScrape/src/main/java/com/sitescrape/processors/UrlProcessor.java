package com.sitescrape.processors;

import com.sitescrape.util.CommandLineUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Deslyxia on 6/22/17.
 */
public class UrlProcessor  extends CommandLineUtil {

    private static Integer urlSuccess = 0;
    private static Integer urlFailed = 0;
    private static Integer urlProcessed = 0;

    private static Options createOptions() {
        Options options = new Options();
        Option url = Option.builder("u")
                .longOpt("url")
                .argName("URL_INPUT")
                .desc("Single URL or , delimited list of URLS to scrape and catalog")
                .required(true)
                .type(String.class)
                .hasArg()
                .build();
        options.addOption(url);

        return options;
    }

    public static void main (String[] args) throws IOException {
        ZonedDateTime startTime = ZonedDateTime.now();

        // Build Options
        addOptions(createOptions());
        CommandLine cmd = parseCommandLine(args,UrlSiteScrape.class.getName());


        // Pull in URL/URLs
        String urlString = StringUtils.trimToNull(cmd.getOptionValue("url"));
        List<String> aliIdStringList = Arrays.asList(urlString.split(","));


        //Process URLs
        try {
            aliIdStringList.parallelStream().forEach(f -> {
                        if (urlWork(f)) {
                            urlProcessed += 1;
                            urlSuccess += 1;
                        } else {
                            urlProcessed += 1;
                            urlFailed += 1;
                        }
                    }
            );
        } catch (Exception e){
            System.out.println("Exception Thrown in Main Method");
            e.printStackTrace();
        }

        //Show Results
        printResults(startTime);
    }

    private static boolean urlWork (String url) {
       try {
           UrlSiteScrape scraper = new UrlSiteScrape();
           scraper.siteScrape(url);
       } catch (Exception e) {
           System.out.println("URL : " + url + " failed to process successfully.");
           e.printStackTrace();
           return false;
       }
        return true;
    }

    private static void printResults(ZonedDateTime startTime) {
        System.out.println("Results");
        System.out.println("Total URLs processed : " + urlProcessed);
        System.out.println("Total Success : " + urlSuccess);
        System.out.println("Total Failures: " + urlFailed);
        System.out.println("Start Time : " + startTime.format(DateTimeFormatter.RFC_1123_DATE_TIME).toString());
        System.out.println("End Time : " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME).toString());
    }
}
