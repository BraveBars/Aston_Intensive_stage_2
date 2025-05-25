package com.homework.task_2;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserDao userDao = new UserDaoImpl();

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
                default  -> System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }

        HibernateUtil.shutdown();
        scanner.close();
    }

    private static void createUser(Scanner sc) {
        System.out.print("Введите имя: ");
        String name = sc.nextLine();
        System.out.print("Введите email: ");
        String email = sc.nextLine();
        System.out.print("Введите возраст: ");
        int age = Integer.parseInt(sc.nextLine());

        User user = new User(name, email, age);
        userDao.create(user);
        System.out.println("Пользователь создан: " + user);
    }

    private static void readUser(Scanner sc) {
        System.out.print("Введите ID пользователя: ");
        long id = Long.parseLong(sc.nextLine());
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

    private static void updateUser(Scanner sc) {
        System.out.print("Введите ID пользователя для обновления: ");
        long id = Long.parseLong(sc.nextLine());
        User user = userDao.read(id);
        if (user == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
            return;
        }

        System.out.print("Новое имя (" + user.getName() + "): ");
        String name = sc.nextLine();
        if (!name.isBlank()) user.setName(name);

        System.out.print("Новый email (" + user.getEmail() + "): ");
        String email = sc.nextLine();
        if (!email.isBlank()) user.setEmail(email);

        System.out.print("Новый возраст (" + user.getAge() + "): ");
        String ageInput = sc.nextLine();
        if (!ageInput.isBlank()) user.setAge(Integer.parseInt(ageInput));

        userDao.update(user);
        System.out.println("Данные пользователя обновлены: " + user);
    }

    private static void deleteUser(Scanner sc) {
        System.out.print("Введите ID пользователя для удаления: ");
        long id = Long.parseLong(sc.nextLine());
        userDao.delete(id);
        System.out.println("Пользователь с ID " + id + " удалён.");
    }
}
