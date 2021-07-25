package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2016, Month.APRIL, 29,20,0), "Ужин", 510)
        );

        System.out.println( getFilteredWithExceeded(mealList, LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2016, Month.MAY, 29, 21, 0), 2000));
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalDateTime startTime,
                                                                    LocalDateTime endTime, int caloriesPerDay) {

        MealListFilter filter = new MealListFilter(mealList, startTime, endTime, caloriesPerDay);
        return filter.getFilteredMealsWithExceed();
    }
}
