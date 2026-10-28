package phonebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PhoneBookServiceTest {

    @Mock
    PhoneBookRepository repository;

    @InjectMocks
    PhoneBookService service;

    @Test
    void addSavesValidName() {
        service.add("Anna", "123");
        verify(repository).save("Anna", "123");
    }

    @Test
    void findReturnsPhoneFromRepository() {
        when(repository.findPhone("Anna")).thenReturn(Optional.of("123"));
        assertEquals(Optional.of("123"), service.findPhone("Anna"));
    }

    @Test
    void findReturnsEmptyForUnknownName() {
        when(repository.findPhone("Bob")).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), service.findPhone("Bob"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void invalidNameIsRejectedWithoutTouchingRepository(String name) {
        assertThrows(IllegalArgumentException.class, () -> service.add(name, "123"));
        assertThrows(IllegalArgumentException.class, () -> service.findPhone(name));
        verifyNoInteractions(repository);
    }
}
