package phonebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class PhoneBookRepositoryTest {

    @Test
    void savesAndFindsAndOverwrites() {
        try (PhoneBookRepository repo = new PhoneBookRepository("jdbc:h2:mem:repotest")) {
            assertEquals(Optional.empty(), repo.findPhone("Anna"));
            repo.save("Anna", "123");
            assertEquals(Optional.of("123"), repo.findPhone("Anna"));
            repo.save("Anna", "456");
            assertEquals(Optional.of("456"), repo.findPhone("Anna"));
        }
    }
}
