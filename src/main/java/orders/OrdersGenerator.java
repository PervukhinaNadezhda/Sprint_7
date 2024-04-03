package orders;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class OrdersGenerator {

    private static final String PATTER_DATE = "yyyy-MM-dd";

    public static Orders random() {
        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final int metroStation = ThreadLocalRandom.current().nextInt(1, 9);
        final String phone = RandomStringUtils.randomNumeric(11);
        final int rentTime = ThreadLocalRandom.current().nextInt(1, 7);
        final String deliveryDate = OffsetDateTime
                .now()
                .plusDays(2)
                .format(DateTimeFormatter.ofPattern(PATTER_DATE));
        final String comment = RandomStringUtils.randomAlphabetic(10);
        final String[] color = {};

        return new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
