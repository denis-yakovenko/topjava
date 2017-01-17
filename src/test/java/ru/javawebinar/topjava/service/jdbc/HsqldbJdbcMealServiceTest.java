package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

/**
 * Created by admin on 15.01.2017.
 */
@ActiveProfiles({Profiles.HSQLDB, Profiles.JDBC})
public class HsqldbJdbcMealServiceTest extends MealServiceTest {
    @Override
    public void testGetWithUser() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        throw new UnsupportedOperationException();
    }
}
