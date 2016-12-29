package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_BREAKFAST_30_ID = START_SEQ + 2;
    public static final int USER_LUNCH_30_ID = START_SEQ + 3;
    public static final int USER_DINNER_30_ID = START_SEQ + 4;
    public static final int USER_BREAKFAST_31_ID = START_SEQ + 5;
    public static final int USER_LUNCH_31_ID = START_SEQ + 6;
    public static final int USER_DINNER_31_ID = START_SEQ + 7;
    public static final int ADMIN_LUNCH_ID = START_SEQ + 8;
    public static final int ADMIN_DINNER_ID = START_SEQ + 9;

    public static Meal USER_BREAKFAST_30 = new Meal(USER_BREAKFAST_30_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static Meal USER_LUNCH_30 = new Meal(USER_LUNCH_30_ID, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static Meal USER_DINNER_30 = new Meal(USER_DINNER_30_ID, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static Meal USER_BREAKFAST_31 = new Meal(USER_BREAKFAST_31_ID, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static Meal USER_LUNCH_31 = new Meal(USER_LUNCH_31_ID, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static Meal USER_DINNER_31 = new Meal(USER_DINNER_31_ID, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static Meal ADMIN_LUNCH = new Meal(ADMIN_LUNCH_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static Meal ADMIN_DINNER = new Meal(ADMIN_DINNER_ID, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

}
