package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds;
        Map<LocalDate, Integer> caloriesByDays = new HashMap<>();
        for (UserMeal userMeal: mealList)
            caloriesByDays.merge(LocalDate.from(userMeal.getDateTime()), userMeal.getCalories(), (oldValue, newValue) -> oldValue + newValue);
        /* solution with use of .stream().map */
        userMealWithExceeds = mealList
                .stream()
                .filter(
                        userMeal -> TimeUtil.isBetween(LocalTime.from(userMeal.getDateTime()), startTime, endTime)
                )
                .map(
                        userMeal ->
                                new UserMealWithExceed(
                                        userMeal.getDateTime(),
                                        userMeal.getDescription(),
                                        userMeal.getCalories(),
                                        caloriesByDays.getOrDefault(LocalDate.from(userMeal.getDateTime()), 0) > caloriesPerDay
                                )

                )
                .collect(Collectors.toList());
        /* another solution with use of .stream().forEach */
        /*
        mealList
                .stream()
                .filter(
                        userMeal -> TimeUtil.isBetween(LocalTime.from(userMeal.getDateTime()), startTime, endTime)
                )
                .forEach(
                    userMeal -> userMealWithExceeds.add(
                        new UserMealWithExceed(
                            userMeal.getDateTime(),
                            userMeal.getDescription(),
                            userMeal.getCalories(),
                            caloriesByDays.getOrDefault(LocalDate.from(userMeal.getDateTime()), 0) > caloriesPerDay
                        )
                    )
                );*/
        return userMealWithExceeds;
    }
}
