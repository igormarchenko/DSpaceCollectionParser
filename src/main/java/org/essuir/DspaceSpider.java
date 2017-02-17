package org.essuir;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * Created by Igor on 16.02.2017.
 */
public class DspaceSpider {
    private static Logger log = Logger.getLogger(DspaceSpider.class.getName());
    private Queue<String> errorPages = new LinkedBlockingQueue<>();

    private List<String> urls = new ArrayList<>();

    private Long collectionId;

    public DspaceSpider(Long collectionId) {
        this.collectionId = collectionId;
    }

    public void parse() {
        String url = String.format("%sjspui/handle/123456789/%d", Config.getDspaceUrl(), collectionId);

        for (int offset = 0; ; offset += Config.getOFFSET()) {
            String page = url + "?offset=" + offset;
            if (!processPage(page)) {
                break;
            }
        }

        log.warning("We have " + errorPages.size() + " errors.");
        while (!errorPages.isEmpty()) {
            String page = errorPages.poll();
            processPage(page);
        }
    }


    private Boolean processPage(String page) {
        log.info("Parsing page " + page);
        Parser parser = new Parser(page);
        parser.parse();

        if (parser.getStatus().equals(Status.SUCCESS)) {
            log.info("Collected " + parser.getLinks().size() + " links.");
            urls.addAll(parser.getLinks());
        } else {
            log.warning("Errors occurred.");
            errorPages.add(page);
        }
        return !parser.getLinks().isEmpty();
    }

    public List<String> getUrls() {
        return urls;
    }
}
