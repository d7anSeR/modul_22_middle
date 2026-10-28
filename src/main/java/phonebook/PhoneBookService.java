package phonebook;

import java.util.Optional;

public class PhoneBookService {

    private final PhoneBookRepository repository;

    public PhoneBookService(PhoneBookRepository repository) {
        this.repository = repository;
    }

    public void add(String name, String phone) {
        validateName(name);
        repository.save(name, phone);
    }

    public Optional<String> findPhone(String name) {
        validateName(name);
        return repository.findPhone(name);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не должно быть пустым или null");
        }
    }
}
