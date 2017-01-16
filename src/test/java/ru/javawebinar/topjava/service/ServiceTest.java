package ru.javawebinar.topjava.service;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class ServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    static final Logger LOG = LoggerFactory.getLogger(ServiceTest.class);
    static StringBuilder results = new StringBuilder();

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        LOG.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------\n" +
                results +
                "---------------------------------\n");
    }

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testSave() throws Exception {}

    @Test
    public void testDuplicateMailSave() throws Exception {}

    @Test
    public void testDelete() throws Exception {}

    @Test
    public void testNotFoundDelete() throws Exception {}

    @Test
    public void testGet() throws Exception {}

    @Test
    public void testGetNotFound() throws Exception {}

    @Test
    public void testGetByEmail() throws Exception {}

    @Test
    public void testGetAll() throws Exception {}

    @Test
    public void testGetBetween() throws Exception {}

    @Test
    public void testUpdate() throws Exception {}

    @Test
    public void testUpdateNotFound() throws Exception {}

    @Test
    public void testGetMeals() throws Exception {}

    @Test
    public void testUser() throws Exception {}
}
