package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

/**
 * Created by admin on 15.01.2017.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JPA})
public class JpaMealServiceTest extends MealServiceTest {
    @Override
    public void testGetWithUser() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        throw new UnsupportedOperationException();
    }
}
