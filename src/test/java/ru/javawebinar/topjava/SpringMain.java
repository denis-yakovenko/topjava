package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {

            // Context with real DB
            appCtx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.DATAJPA);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            // Context with mock DB
            // appCtx.load("spring/spring-app.xml", "spring/mock.xml");
            appCtx.refresh();
            /*System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);*/

            // Testing getWithUser method
            MealService mealService = appCtx.getBean(MealService.class);
            /*Meal m = mealService.getWithUser(100007,100000);
            System.out.println(m.getUser());*/

            // Testing getWithMeals method
            /*UserService userService = appCtx.getBean(UserService.class);
            User u = userService.getWithMeals(100000);
            System.out.println(u.getMeals());*/

            //System.out.println("*********************************");
            // Test amount of transaction when save meal
            Meal created = new Meal(999, of(2020, Month.DECEMBER, 31, 23, 59), "Новогодний Ужин", 2500);
            mealService.save(created, USER_ID);
            //System.out.println("*********************************");
        }
    }
}
