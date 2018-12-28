package com.agileengine;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsoupFindByIdSnippet {

    private static Logger LOGGER = LoggerFactory.getLogger(JsoupFindByIdSnippet.class);

    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {

        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case
        String resourcePath = "./samples/original.html";
        String resourcePath2 = "./samples/case1.html";
        String targetElementId = "make-everything-ok-button";
        String cssQuery = "a";

        Optional<Element> buttonOpt = findElementById(new File(resourcePath), targetElementId);

        Optional<SearchModel> stringifiedAttributesOpt = buttonOpt.map(button ->
                convertToModel(button));

        List<SearchModel> inputSearchModels = findElementsByQuery(new File(resourcePath2), cssQuery)
                .map(o -> o.stream()
                        .map(a -> convertToModel(a))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        stringifiedAttributesOpt.ifPresent(attrs -> LOGGER.info("Target element attrs: [{}]", attrs));

        LOGGER.info("Our candidate: [{}]",searchBestModel(inputSearchModels, stringifiedAttributesOpt.get()));

    }

    private static int compareSearchModel(SearchModel etalon, SearchModel candidate) {

        int score = 0;

        if (candidate.getId().equals(etalon.getId())) {
            score++;
        }
        if (candidate.getClassNames().containsAll(etalon.getClassNames())) {
            score++;
        }
        if (candidate.getHref().equals(etalon.getHref())) {
            score++;
        }
        if (candidate.getTitle().equals(etalon.getTitle())) {
            score++;
        }
        if (candidate.getTextButton().equals(etalon.getTextButton())) {
            score++;
        }

        return score;
    }

    private static SearchModel searchBestModel(List<SearchModel> searchModels, SearchModel etalon) {

        Map<SearchModel, Integer> candidateRating = searchModels.stream()
                .collect(Collectors.toMap(Function.identity(), o -> compareSearchModel(etalon, o)));

        return candidateRating.entrySet().stream()
                .max(Comparator.comparing(o -> o.getValue()))
                .map(o -> o.getKey())
                .get();
    }

    private static SearchModel convertToModel(Element element) {
        String id = element.id();
        Set<String> classNames = element.classNames();
        String href = element.attr("href");
        String title = element.attr("title");
        String rel = element.attr("rel");
        String textButton = element.ownText();

        SearchModel e = new SearchModel(id, classNames, href, title, rel, textButton);
        return e;

    }

    private static Optional<Elements> findElementsByQuery(File htmlFile, String cssQuery) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.select(cssQuery));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

}