package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:id  AND m.dateTime BETWEEN :sd AND :ed ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("sd") LocalDateTime startDate, @Param("ed") LocalDateTime endDate, @Param("id") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:id ORDER BY m.dateTime DESC")
    List<Meal> findAll(@Param("id") int userId);

    @Query("SELECT m FROM Meal m WHERE m.id=:id and m.user.id=:userId")
    Meal findOne(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT u from User u where u.id=(select m.user.id from Meal m where m.id=:id)")
    User getUser(@Param("id") int id);
}
