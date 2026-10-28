package converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class ConverterApp {

    public static void main(String[] args) throws IOException {
        CurrencyConverter converter = new CurrencyConverter(new BigDecimal("90.00")); // ponytail: курс захардкожен, вынести в аргумент/конфиг при необходимости
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Сумма в долларах: ");
        System.out.println(converter.usdToRub(in.readLine()) + " RUB");
    }
}
