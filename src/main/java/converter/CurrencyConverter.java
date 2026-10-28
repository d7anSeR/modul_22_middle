package converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

    private final BigDecimal rate;

    public CurrencyConverter(BigDecimal rate) {
        if (rate == null || rate.signum() <= 0) {
            throw new IllegalArgumentException("Курс должен быть положительным числом");
        }
        this.rate = rate;
    }

    public BigDecimal usdToRub(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Ввод пуст");
        }
        BigDecimal usd;
        try {
            usd = new BigDecimal(input.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Не число: " + input, e);
        }
        if (usd.signum() < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной: " + input);
        }
        return usd.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
