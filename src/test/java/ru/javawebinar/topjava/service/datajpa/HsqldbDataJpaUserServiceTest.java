package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

/**
 * Created by admin on 15.01.2017.
 */
@ActiveProfiles({Profiles.HSQLDB, Profiles.DATAJPA})
public class HsqldbDataJpaUserServiceTest extends UserServiceTest {
}
