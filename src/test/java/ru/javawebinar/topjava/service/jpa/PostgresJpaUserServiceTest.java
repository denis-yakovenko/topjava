package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by admin on 15.01.2017.
 */
@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class PostgresJpaUserServiceTest extends UserServiceTest {
    @Override
    public void testGetWithMeals() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        throw new UnsupportedOperationException();
    }
}
