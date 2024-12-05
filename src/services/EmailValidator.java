package services;

import interfaces.Validator;

import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[\\w._-]+@([\\w-]+\\.)+[A-Za-z]{2,}$"
    );

    @Override
    public boolean isValid(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Вхідний текст не може бути null або порожній");
        }

        return EMAIL_PATTERN.matcher(text).matches();
    }
}
