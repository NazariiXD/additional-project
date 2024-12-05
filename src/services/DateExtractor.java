package services;

import interfaces.Extractor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateExtractor implements Extractor<LocalDate> {
    private final Pattern datePattern;
    private final DateTimeFormatter dateTimeFormatter;

    public DateExtractor(String dateFormat) {
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        datePattern = createDatePattern(dateFormat);
    }

    @Override
    public List<LocalDate> extractAll(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Вхідний текст не може бути null");
        }

        List<LocalDate> dates = new ArrayList<>();
        Matcher matcher = datePattern.matcher(text);

        while (matcher.find()) {
            LocalDate localDate = parseDate(matcher.group());
            if (localDate != null) {
                dates.add(localDate);
            }
        }

        return dates;
    }

    private Pattern createDatePattern(String dateFormat) {
        String regex = dateFormat
                .replace("dd", "\\d{2}")
                .replace("MM", "\\d{2}")
                .replace("yyyy", "\\d{4}");

        return Pattern.compile("\\b" + regex + "\\b");
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, dateTimeFormatter);
        }
        catch (DateTimeParseException e) {
            return null;
        }
    }
}
