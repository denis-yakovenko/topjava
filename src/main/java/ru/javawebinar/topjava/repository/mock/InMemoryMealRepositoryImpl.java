package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m->this.save(m, 2)); // userId = 2, to test throwing NotFoundException in the get/delete methods of the controller
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            Meal existingMeal4Update = repository.get(meal.getId());
            if (!existingMeal4Update.getUserId().equals(userId)) return null;
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.remove(id);
        return !(meal == null || !meal.getUserId().equals(userId));
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return (meal == null || !meal.getUserId().equals(userId)) ? null : meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAll(userId, LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX);
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<Meal> result = new ArrayList<>(
                repository
                        .values()
                        .stream()
                        .filter(meal -> meal.getUserId()==userId)
                        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), startDate, endDate))
                        .collect(Collectors.toList())
        );
        Collections.sort(result, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return Optional.of(result).orElseGet(Collections::emptyList);
    }
}

