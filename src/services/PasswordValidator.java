package services;

import interfaces.Validator;

import java.util.regex.Pattern;

public class PasswordValidator implements Validator {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-ZА-Я])(?=.*[a-zа-я])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$"
    );

    @Override
    public boolean isValid(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Вхідний текст не може бути null або порожній");
        }

        return PASSWORD_PATTERN.matcher(text).matches();
    }
}
