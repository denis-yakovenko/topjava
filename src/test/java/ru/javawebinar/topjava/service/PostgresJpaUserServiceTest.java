package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by admin on 15.01.2017.
 */
@ActiveProfiles({Profiles.POSTGRES, Profiles.JPA})
public class PostgresJpaUserServiceTest extends UserServiceTest {
    @Override
    public void testGetMeals() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        throw new UnsupportedOperationException();
    }
}
