package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealDao {
    Meal save(Meal meal);
    void delete (Integer id);
    Collection<Meal> getAll();
    Meal get (Integer id);
}
