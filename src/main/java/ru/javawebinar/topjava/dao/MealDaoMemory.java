package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMemory implements MealDao {

    private AtomicInteger counter = new AtomicInteger(0);
    private Map<Integer, Meal> data = new ConcurrentHashMap<>();

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()){
            meal.setId(counter.incrementAndGet());
        }
        return data.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        data.remove(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return data.values();
    }

    @Override
    public Meal get(Integer id) {
        return data.get(id);
    }
}
