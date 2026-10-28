package converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CurrencyConverterTest {

    private final CurrencyConverter converter = new CurrencyConverter(new BigDecimal("90"));

    @ParameterizedTest
    @ValueSource(strings = {"10", " 10 ", "10.00", "10,0"})
    void convertsValidInput(String input) {
        assertEquals(new BigDecimal("900.00"), converter.usdToRub(input));
    }

    @Test
    void zeroIsAllowed() {
        assertEquals(new BigDecimal("0.00"), converter.usdToRub("0"));
    }

    @Test
    void roundsToKopecks() {
        assertEquals(new BigDecimal("0.45"), converter.usdToRub("0.005"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "abc", "12abc", "1.2.3", "NaN", "Infinity", "-1", "-0.01"})
    void invalidInputThrows(String input) {
        assertThrows(IllegalArgumentException.class, () -> converter.usdToRub(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-5"})
    void nonPositiveRateThrows(String rate) {
        assertThrows(IllegalArgumentException.class, () -> new CurrencyConverter(new BigDecimal(rate)));
    }

    @Test
    void nullRateThrows() {
        assertThrows(IllegalArgumentException.class, () -> new CurrencyConverter(null));
    }
}
