package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Profile(Profiles.POSTGRES)
public class PostgresJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public PostgresJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }
}
