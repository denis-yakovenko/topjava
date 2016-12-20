package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private static final Logger LOG = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("save " + meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getAll");
        Collection<MealWithExceed> allMeals = MealsUtil.getWithExceeded(
                service.getAll(AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay());
        List<MealWithExceed> result = new ArrayList<>(
                allMeals
                        .stream()
                        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                        .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(), startDate, endDate))
                        .collect(Collectors.toList())
        );
        result.forEach(u->LOG.info(u.toString()));
        return Optional.of(result).orElseGet(Collections::emptyList);
    }

    public Collection<MealWithExceed> getAll() {
        return getAll(LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX);
    }

}
