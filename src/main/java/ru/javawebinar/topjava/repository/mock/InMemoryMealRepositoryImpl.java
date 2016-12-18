package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m->this.save(m, 2)); // userId = 2, to test throwing NotFoundException in the get/delete methods of the controller
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        }
        repository.put(meal.getId(), meal);
        LOG.info("save " + meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete " + id);
        Meal meal = repository.remove(id);
        if (meal==null) {
            LOG.info("no mapping for key "+id);
            return false;
        }
        if (!meal.getUserId().equals(userId)) {
            LOG.info("wrong user "+id);
            return false;
        }
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get " + id);
        Meal meal = repository.get(id);
        if (meal==null) {
            LOG.info("no mapping for key "+id);
            return null;
        }
        if (!meal.getUserId().equals(userId)) {
            LOG.info("wrong user "+id);
            return null;
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.info("getAll");
        List<Meal> result = new ArrayList<>(
                repository
                        .values()
                        .stream()
                        .filter(meal -> meal.getUserId().equals(userId))
                        .collect(Collectors.toList())
        );
        Collections.sort(result, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        result.forEach(m->LOG.info(m.toString()));
        return Optional.of(result).orElseGet(Collections::emptyList);
    }
}

