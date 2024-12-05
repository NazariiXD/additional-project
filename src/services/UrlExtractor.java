package services;

import interfaces.Extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlExtractor implements Extractor<String> {
    private final Pattern URL_PATTERN = Pattern.compile(
            "(https?|ftp)://(?:www\\.)?[a-zA-Z0-9-]+(?:\\.[a-zA-Z]{2,})+(?:/[a-zA-Z0-9-_.?&%=#]*)*"
    );

    @Override
    public List<String> extractAll(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Вхідний текст не може бути null");
        }

        List<String> urls = new ArrayList<>();
        Matcher matcher = URL_PATTERN.matcher(text);

        while (matcher.find()) {
            urls.add(matcher.group());
        }

        return urls;
    }
}
