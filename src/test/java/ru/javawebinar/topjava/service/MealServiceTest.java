package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_BREAKFAST_30_ID,USER_ID);
        MATCHER.assertEquals(USER_BREAKFAST_30, meal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(ADMIN_DINNER_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_LUNCH), service.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDates() throws Exception {
        Collection<Meal> all = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31), LocalDate.of(2015, Month.MAY, 31), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_DINNER_31, USER_LUNCH_31, USER_BREAKFAST_31), all);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        Collection<Meal> all = service.getBetweenDateTimes(
                LocalDateTime.of(2015, Month.MAY, 30, 12, 0),
                LocalDateTime.of(2015, Month.MAY, 31, 15, 0),
                USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_LUNCH_31, USER_BREAKFAST_31, USER_DINNER_30, USER_LUNCH_30), all);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> all = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_DINNER, ADMIN_LUNCH), all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_BREAKFAST_30);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(300);
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(USER_BREAKFAST_30.getId(), USER_ID));
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 17, 0), "Админ между ланчем и ужином", 800);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_DINNER, newMeal, ADMIN_LUNCH), service.getAll(ADMIN_ID));
    }

    /*3. Сделать тесты на чужих юзеров (delete, get, update) с тем чтобы получить NotFoundException и тесты на update чужой еды.*/

    @Test(expected = NotFoundException.class)
    public void updateWithWrongUser() throws Exception {
        Meal updated = service.get(USER_BREAKFAST_30.getId(), USER_ID);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(300);
        service.update(updated, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUser() throws Exception{
        service.get(USER_BREAKFAST_30.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithWrongUser() throws Exception{
        service.delete(USER_BREAKFAST_30.getId(), ADMIN_ID);
    }
}