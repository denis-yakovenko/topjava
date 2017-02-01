package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * Created by admin on 31.01.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(
                        MATCHERWITHEXCEED.contentListMatcher(
                                MealsUtil.getWithExceeded(
                                        mealService.getAll(
                                                USER_ID
                                        ),
                                        DEFAULT_CALORIES_PER_DAY
                                )
                        )
                )
        );
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        updated.setCalories(999);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testCreate() throws Exception {
        Meal created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isOk());
        Meal returned = MATCHER.fromJsonAction(action);
        created.setId(returned.getId());
        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(mockMvc.perform(
                get(REST_URL + "filter")
                        /*.param("startDateTime","2015-05-30T00:00:00.000")
                        .param("endDateTime","2015-05-30T23:59:00.000")*/
                        .param("startDateTime","2015-05-30 00:00")
                        .param("endDateTime","2015-05-30 23:59")
                )
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(MATCHERWITHEXCEED.contentListMatcher(
                                MealsUtil.getWithExceeded(
                                        mealService.getBetweenDateTimes(
                                                LocalDateTime.of(2015, Month.MAY, 30, 0, 0),
                                                LocalDateTime.of(2015, Month.MAY, 30, 23, 59),
                                                USER_ID),
                                        DEFAULT_CALORIES_PER_DAY
                                )
                        ))
        );
    }

}
