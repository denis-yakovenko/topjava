package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDao {
    Integer getId();
    void add (Meal meal);
    void update (Integer id, Meal meal);
    void delete (Integer id);
    List<MealWithExceed> getAll();
    Meal get (Integer id);
}
