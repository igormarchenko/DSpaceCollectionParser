package org.essuir;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Parser {
    private static Logger log = Logger.getLogger(Parser.class.getName());
    private List<String> urls = new ArrayList<>();
    private Status status;
    private String url;

    public Parser(String url) {
        this.url = url;
    }

    public void parse() {
        Optional<Document> pageContent = getPageContent(url);

        if (pageContent.isPresent()) {
            List<String> links = parseLinksOnPage(pageContent.get());
            urls.addAll(links);
            log.info("Parsed " + links.size() + " links");
        }
    }

    private Optional<Document> getPageContent(String url) {
        try {
            status = Status.SUCCESS;
            return Optional.of(Jsoup.connect(url).timeout(60_000).get());
        } catch (IOException e) {
            log.severe("Couldn't open URL: " + url);
            status = Status.ERROR;
        }
        return Optional.empty();
    }

    private List<String> parseLinksOnPage(Document document) {
        return document.select("strong a")
                .stream()
                .map(element -> element.attr("href").replace("/jspui/handle/", ""))
                .filter(link -> link.matches("123456789/\\d+"))
                .filter(link -> !link.matches("123456789/1"))
                .map(link -> link.replace("123456789/", ""))
                .collect(Collectors.toList());
    }

    public List<String> getLinks() {
        return urls;
    }

    public Status getStatus() {
        return status;
    }
}
