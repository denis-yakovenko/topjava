package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDaoMemory implements MealDao {
    private static final Logger LOG = getLogger(MealDaoMemory.class);

    private AtomicInteger counter = new AtomicInteger(0);
    private final List<Meal> data = Collections.synchronizedList(new ArrayList<>());

    public MealDaoMemory() {
        data.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, getId()));
        data.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, getId()));
        data.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, getId()));
        data.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, getId()));
        data.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, getId()));
        data.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, getId()));
    }

    @Override
    public Integer getId(){
        return counter.incrementAndGet();
    }

    @Override
    public void add(Meal meal) {
        data.add(meal);
    }

    @Override
    public void update(Integer id, Meal meal) {
        synchronized (data) {
            data.stream().filter(mealItem -> mealItem.getId() == id).forEach(mealItem -> {
                data.set(data.indexOf(mealItem), meal);
                LOG.debug("id:" + id + " " + meal.toString());
            });
        }
    }

    @Override
    public void delete(Integer id) {
        synchronized (data) {
            Iterator<Meal> iterator = data.iterator();
            while (iterator.hasNext()) {
                Meal meal = iterator.next();
                if (meal.getId() == id) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public List<MealWithExceed> getAll() {
        return MealsUtil.getAllWithExceed(data);
    }

    @Override
    public Meal get(Integer id) {
        synchronized (data) {
            for (Meal meal : data) {
                if (meal.getId() == id) {
                    return meal;
                }
            }
        }
        return null;
    }
}
