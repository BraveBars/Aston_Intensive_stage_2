package com.homework.task_2;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final UserDao userDao = new UserDaoImpl();

    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} ]{2,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1. Создать пользователя");
            System.out.println("2. Показать пользователя по ID");
            System.out.println("3. Показать всех пользователей");
            System.out.println("4. Обновить данные пользователя");
            System.out.println("5. Удалить пользователя");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> createUser(scanner);
                case "2" -> readUser(scanner);
                case "3" -> readAllUsers();
                case "4" -> updateUser(scanner);
                case "5" -> deleteUser(scanner);
                case "0" -> running = false;
                default -> System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }

        HibernateUtil.shutdown();
        scanner.close();
    }

    private static void createUser(Scanner scanner) {
        String name = readValidName(scanner);
        String email = readValidEmail(scanner);
        int age = readValidAge(scanner);

        User user = new User(name, email, age);
        userDao.create(user);
        System.out.println("Пользователь создан: " + user);
    }

    private static void readUser(Scanner scanner) {
        System.out.print("Введите ID пользователя: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        User user = userDao.read(id);
        if (user != null) {
            System.out.println("Найден пользователь: " + user);
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    private static void readAllUsers() {
        List<User> users = userDao.readAll();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
        } else {
            System.out.println("Список всех пользователей:");
            users.forEach(System.out::println);
        }
    }

    private static void updateUser(Scanner scanner) {
        System.out.print("Введите ID пользователя для обновления: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        User user = userDao.read(id);
        if (user == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
            return;
        }

        System.out.println("Оставьте поле пустым, чтобы не менять.");

        System.out.print("Новое имя (" + user.getName() + "): ");
        String nameInput = scanner.nextLine().trim();
        if (!nameInput.isBlank()) {
            if (NAME_PATTERN.matcher(nameInput).matches()) {
                user.setName(nameInput);
            } else {
                System.out.println("Имя не изменено — неверный формат.");
            }
        }

        System.out.print("Новый email (" + user.getEmail() + "): ");
        String emailInput = scanner.nextLine().trim();
        if (!emailInput.isBlank()) {
            if (EMAIL_PATTERN.matcher(emailInput).matches()) {
                user.setEmail(emailInput);
            } else {
                System.out.println("Email не изменён — неверный формат.");
            }
        }

        System.out.print("Новый возраст (" + user.getAge() + "): ");
        String ageInput = scanner.nextLine().trim();
        if (!ageInput.isBlank()) {
            try {
                int age = Integer.parseInt(ageInput);
                if (age >= 1 && age <= 150) {
                    user.setAge(age);
                } else {
                    System.out.println("Возраст не изменён — вне диапазона.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Возраст не изменён — неверный ввод.");
            }
        }

        userDao.update(user);
        System.out.println("Данные пользователя обновлены: " + user);
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("Введите ID пользователя для удаления: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        userDao.delete(id);
        System.out.println("Пользователь с ID " + id + " удалён.");
    }

    private static String readValidName(Scanner scanner) {
        while (true) {
            System.out.print("Введите имя (2–50 букв): ");
            String name = scanner.nextLine().trim();
            if (NAME_PATTERN.matcher(name).matches()) {
                return name;
            }
            System.out.println("Неверный формат имени. Разрешены только буквы и пробелы.");
        }
    }

    private static String readValidEmail(Scanner scanner) {
        while (true) {
            System.out.print("Введите email: ");
            String email = scanner.nextLine().trim();
            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email;
            }
            System.out.println("Неверный формат email. Например: example@example.com");
        }
    }

    private static int readValidAge(Scanner scanner) {
        while (true) {
            System.out.print("Введите возраст от 1 до 150): ");
            String input = scanner.nextLine().trim();
            try {
                int age = Integer.parseInt(input);
                if (age >= 1 && age <= 150) {
                    return age;
                } else {
                    System.out.println("Возраст должен быть от 1 до 150.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }
}
