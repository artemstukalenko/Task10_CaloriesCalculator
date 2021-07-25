package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealListFilter {

    private static int caloriesPerDay;
    private static LocalDateTime startTime;
    private static LocalDateTime endTime;

    private List<UserMeal> mealList;
    private Map<LocalDate, List<UserMeal>> mealFilteredByDateAndTime;
    private Map<LocalDate, List<UserMealWithExceed>> resultingMap = new HashMap<>();

    public MealListFilter(List<UserMeal> mealList, LocalDateTime startTime,
                          LocalDateTime endTime, int caloriesPerDay) {
        this.mealList = mealList;
        this.startTime = startTime;
        this.endTime = endTime;
        this.caloriesPerDay = caloriesPerDay;
    }

    public List<UserMealWithExceed> getFilteredMealsWithExceed() {
        mealFilteredByDateAndTime = filterMealMapByDateAndTime();

        for (Map.Entry<LocalDate, List<UserMeal>> entry : mealFilteredByDateAndTime.entrySet()) {
            resultingMap.put(entry.getKey(), getListWithExceed(entry.getValue()));
        }

        return getFilteredMealsInList();
    }

    private List<UserMealWithExceed> getFilteredMealsInList() {
        List<UserMealWithExceed> resulting = new ArrayList<>();

        for (Map.Entry<LocalDate, List<UserMealWithExceed>> entry : resultingMap.entrySet()) {
            entry.getValue().stream().forEach(resulting::add);
        }

        return resulting;
    }

    private Map<LocalDate, List<UserMeal>> filterMealMapByDateAndTime() {
        Map<LocalDate, List<UserMeal>> filtered = mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime(), startTime, endTime))
                .collect(Collectors.groupingBy(meal -> meal.getDate()));

        return filtered;
    }

    private static List<UserMealWithExceed> getListWithExceed(List<UserMeal> mealList) {
        final int caloriesSum = getSumOfCalories(mealList);

        return mealList.stream()
                .map(meal -> {
                    return new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                            (caloriesSum > caloriesPerDay) ? true : false);
                }).collect(Collectors.toList());
    }

    private static int getSumOfCalories(List<UserMeal> mealList) {
        return mealList.stream().mapToInt(meal -> meal.getCalories())
                .reduce((accumulator, calories) -> accumulator += calories).getAsInt();
    }

}
