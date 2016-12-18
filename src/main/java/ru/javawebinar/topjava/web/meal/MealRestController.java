package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

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

    public Meal save(Meal meal) { return service.save(meal, AuthorizedUser.id()); }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getAll() {
        Collection<MealWithExceed> result = MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
        result.forEach(u->LOG.info(u.toString()));
        return result;
    }

}
