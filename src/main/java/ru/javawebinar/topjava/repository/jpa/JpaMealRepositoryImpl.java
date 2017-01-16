package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(em.getReference(User.class, userId));
            em.persist(meal);
        } else {
            if (em
                    .createNamedQuery(Meal.UPDATE)
                    .setParameter("id",meal.getId())
                    .setParameter("userId",userId)
                    .setParameter("description",meal.getDescription())
                    .setParameter("calories",meal.getCalories())
                    .setParameter("dateTime",meal.getDateTime())
                    .executeUpdate()==0) return null;
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em
                .createNamedQuery(Meal.DELETE)
                .setParameter("id",id)
                .setParameter("userId",userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em
                .createNamedQuery(Meal.ONE, Meal.class)
                .setParameter("id",id)
                .setParameter("userId",userId)
                .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em
                .createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em
                .createNamedQuery(Meal.ALL_SORTED_BETWEEN, Meal.class)
                .setParameter("userId",userId)
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .getResultList();
    }
}