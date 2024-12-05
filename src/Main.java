import interfaces.Extractor;
import interfaces.FileReader;
import interfaces.Validator;
import services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static boolean isRunning = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Validator emailValidator = new EmailValidator();
    private static final Validator passwordValidator = new PasswordValidator();
    private static final Extractor<String> urlExtractor = new UrlExtractor();
    private static final FileReader fileReader = new FileReaderService();

    public static void main(String[] args) {
        while (isRunning) {
            showMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();
            handleChoice(choice);
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Меню задач із регулярними виразами ---");
        System.out.println("1. Перевірка формату email");
        System.out.println("2. Перевірка пароля");
        System.out.println("3. Витяг URL із тексту");
        System.out.println("4. Витяг дат у вказаному форматі із тексту");
        System.out.println("0. Вихід");
        System.out.print("Виберіть опцію: ");
    }

    private static void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1:
                    handleEmailValidation();
                    break;
                case 2:
                    handlePasswordValidation();
                    break;
                case 3:
                    handleUrlExtraction();
                    break;
                case 4:
                    handleDateExtraction();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
        catch (IOException e) {
            System.out.println("Помилка при обробці файлу: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Неочікувана помилка: " + e.getMessage());
        }
    }

    private static void handleEmailValidation() {
        System.out.print("Введіть email для перевірки: ");
        String email = scanner.nextLine();

        if (emailValidator.isValid(email)) {
            System.out.println(email + " є валідним email.");
        }
        else {
            System.out.println(email + " не є валідним email.");
        }
    }

    private static void handlePasswordValidation() {
        System.out.println("Пароль повинен містити:");
        System.out.println("- Принаймні одну велику літеру (A-Z, А-Я)");
        System.out.println("- Принаймні одну малу літеру (a-z, а-я)");
        System.out.println("- Принаймні одну цифру (0-9)");
        System.out.println("- Принаймні один спеціальний символ (!@#$%^&*)");
        System.out.println("- Довжина пароля повинна бути від 8 до 20 символів");
        System.out.print("Введіть пароль для перевірки: ");

        String password = scanner.nextLine();

        if (passwordValidator.isValid(password)) {
            System.out.println(password + " є валідним паролем.");
        }
        else {
            System.out.println(password + " не є валідним паролем.");
        }
    }

    private static void handleUrlExtraction() throws IOException {
        System.out.print("Введіть шлях до файлу для витягання URL: ");
        String filePath = scanner.nextLine();
        String fileContent = fileReader.readAllText(filePath);

        List<String> urls = urlExtractor.extractAll(fileContent);

        if (urls.isEmpty()) {
            System.out.println("Не знайдено URL.");
        }
        else {
            System.out.println("Знайдені URL:");
            urls.forEach(System.out::println);
        }
    }

    private static void handleDateExtraction() throws IOException {
        System.out.print("Введіть шлях до файлу для витягання дат: ");
        String filePath = scanner.nextLine();
        String fileContent = fileReader.readAllText(filePath);

        System.out.print("Введіть формат дати (наприклад, dd-MM-yyyy): ");
        String dateFormat = scanner.nextLine();

        Extractor<LocalDate> dateExtractor = new DateExtractor(dateFormat);
        List<LocalDate> dates = dateExtractor.extractAll(fileContent);

        if (dates.isEmpty()) {
            System.out.println("Не знайдено дат у вказаному форматі.");
        }
        else {
            System.out.println("Знайдені дати:");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            dates.forEach(date -> System.out.println(date.format(formatter)));
        }
    }
}