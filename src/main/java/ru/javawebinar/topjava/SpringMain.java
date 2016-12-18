package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));
            adminUserController.getAll();
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 30, 8, 0), "Breakfast", 50));
            mealRestController.save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 30, 12, 0), "Lunch", 100));
            mealRestController.save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 30, 16, 0), "Dinner", 150));
            mealRestController.save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 31, 8, 0), "Breakfast", 500));
            mealRestController.save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 31, 12, 0), "Lunch", 1000));
            mealRestController.save(new Meal(LocalDateTime.of(2016, Month.DECEMBER, 31, 16, 0), "Dinner", 1500));
            mealRestController.getAll();
            //mealRestController.get(3); //NotFoundException, it's ok!
            //mealRestController.delete(6); //NotFoundException, it's ok!
            //mealRestController.delete(999); //NotFoundException, it's ok!
        }
    }
}
