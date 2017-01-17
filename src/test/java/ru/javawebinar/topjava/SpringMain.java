package ru.javawebinar.topjava;

import org.junit.Assert;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {

            /* Context with real DB */
            appCtx.getEnvironment().setActiveProfiles(Profiles.POSTGRES, Profiles.DATAJPA);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            /* Context with mock DB */
            /*appCtx.load("spring/spring-app.xml", "spring/mock.xml");*/
            appCtx.refresh();
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            /* Checking conformity between repository/dataSource and profiles */
            Assert.assertTrue(Arrays.asList(appCtx.getBeanDefinitionNames()).contains("dataJpaMealRepositoryImpl"));
            Assert.assertTrue(appCtx.getBean("dataSource") instanceof org.apache.tomcat.jdbc.pool.DataSource);

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);

            /* Testing getWithUser method */
            MealService mealService = appCtx.getBean(MealService.class);
            Meal m = mealService.getWithUser(100007,100000);
            System.out.println(m.getUser());

            /* Testing getWithMeals method */
            UserService userService = appCtx.getBean(UserService.class);
            User u = userService.getWithMeals(100000);
            System.out.println(u.getMeals());
        }
    }
}
