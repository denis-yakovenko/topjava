package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;

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
        LOG.debug(request.getParameter("dateTime"));
        //LocalDateTime localDateTime = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);//request.getParameter("dateTime");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String id = request.getParameter("id");
        if(id == null || id.isEmpty())
        {
            Meal meal = new Meal(localDateTime,request.getParameter("description"),Integer.parseInt(request.getParameter("calories")),dao.getId());
            LOG.debug(meal.toString());
            dao.add(meal);
        }
        else
        {
            Meal meal = new Meal(localDateTime,request.getParameter("description"),Integer.parseInt(request.getParameter("calories")),Integer.parseInt(id));
            LOG.debug(meal.toString());
            dao.update(Integer.parseInt(id),meal);
        }
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
        request.setAttribute("meals",dao.getAll());
        if (action==null){
            request.getRequestDispatcher("meals.jsp").forward(request,response);
        } else if (action.equalsIgnoreCase("delete")){
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")){
            request.getRequestDispatcher("meals.jsp").forward(request,response);
        }
    }
}
