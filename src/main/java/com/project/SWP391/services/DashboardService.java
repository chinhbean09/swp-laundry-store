package com.project.SWP391.services;

import com.project.SWP391.responses.DashboardResponse;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<Map.Entry<String, Float>> getRevenueOfWeekFromSpecificDate(Long  targetDateMillis);

    List<Map.Entry<String, Float>> extractAndSummarizeMonthlyRevenue(String month);

    List<Map.Entry<String, Float>> extractAndSummarizeYearlyRevenue(String month);

    DashboardResponse getTotalOfAMonth();
}
