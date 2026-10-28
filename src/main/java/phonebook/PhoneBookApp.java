package phonebook;

import java.util.Scanner;

public class PhoneBookApp {

    public static void main(String[] args) {
        try (PhoneBookRepository repository = new PhoneBookRepository("jdbc:h2:./phonebook");
             Scanner in = new Scanner(System.in)) {
            PhoneBookService service = new PhoneBookService(repository);
            System.out.println("Команды: add <имя> <номер> | get <имя> | exit");
            while (in.hasNextLine()) {
                String[] p = in.nextLine().trim().split("\\s+", 3);
                try {
                    switch (p[0]) {
                        case "add" -> {
                            service.add(p.length > 1 ? p[1] : null, p.length > 2 ? p[2] : null);
                            System.out.println("OK");
                        }
                        case "get" -> System.out.println(
                                service.findPhone(p.length > 1 ? p[1] : null).orElse("не найдено"));
                        case "exit" -> { return; }
                        default -> System.out.println("Неизвестная команда");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }
}
