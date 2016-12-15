package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by admin on 11.12.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private static MealDao dao = new MealDaoMemory();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("MealServlet.doPost");
        String id = request.getParameter("id");
        Meal meal = new Meal(
                id.isEmpty()?null:Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );
        LOG.debug(meal.toString());
        dao.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("MealServlet.doGet");
        String action = request.getParameter("action");
        if (action==null){
        } else if (action.equalsIgnoreCase("delete")){
            dao.delete(Integer.parseInt(request.getParameter("id")));
        } else if (action.equalsIgnoreCase("edit")){
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.get(id);
            request.setAttribute("meal", meal);
            request.setAttribute("id", id);
            request.setAttribute("edit", true);
        }
        request.setAttribute("meals", MealsUtil.getWithExceed(dao.getAll(),2000));
        if (action==null){
            request.getRequestDispatcher("meals.jsp").forward(request,response);
        } else if (action.equalsIgnoreCase("delete")){
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")){
            request.getRequestDispatcher("meals.jsp").forward(request,response);
        }
    }
}
