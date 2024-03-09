package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.Order;
import com.project.SWP391.repositories.OrderRepository;
import com.project.SWP391.repositories.StoreRepository;
import com.project.SWP391.responses.DashboardResponse;
import com.project.SWP391.security.utils.SecurityUtils;
import com.project.SWP391.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    @Override
    public List<Map.Entry<String, Float>>  getRevenueOfWeekFromSpecificDate(Long targetDateMillis) {
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        Map<String, Float> weeklyRevenueMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(targetDateMillis);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        long startTimeMillis = calendar.getTimeInMillis();

        // Lấy ngày kết thúc tuần từ ngày đích
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        long endTimeMillis = calendar.getTimeInMillis();

        // Định dạng để lấy ngày-tháng và thứ từ Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM EEE", Locale.US);

        // Lọc danh sách đơn hàng trong khoảng thời gian
        List<Order> orderList = orderRepository.findAllByStoreId(store.getId());
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getOrderDate() >= startTimeMillis && order.getOrderDate() <= endTimeMillis && order.getIsPaid() == 1) {
                filteredOrders.add(order);
            }
        }
        for (Order order : filteredOrders) {
            Date orderDate = new Date(order.getOrderDate());
            String formattedDate = sdf.format(orderDate);

            // Thêm doanh thu vào danh sách, nếu ngày đã có tồn tại, cộng thêm vào giá trị hiện tại
            weeklyRevenueMap.put(formattedDate, weeklyRevenueMap.getOrDefault(formattedDate, 0.0F) + order.getTotal());



        }

        List<Map.Entry<String, Float>> sortedList = new ArrayList<>(weeklyRevenueMap.entrySet());

        // Sắp xếp danh sách theo giá trị của key (tháng)
        Collections.sort(sortedList, Comparator.comparing(Map.Entry::getKey));
        if(sortedList.isEmpty()){
            return null;
        }
        return sortedList;
    }

    @Override
    public List<Map.Entry<String, Float>> extractAndSummarizeMonthlyRevenue(String targetMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM", Locale.US);
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        List<Order> orderList = orderRepository.findAllByStoreId(store.getId());
        // Lọc danh sách đơn hàng trong khoảng thời gian
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orderList) {
            String orderMonth = sdf.format(new Date(order.getOrderDate()));
            if (orderMonth.equalsIgnoreCase(targetMonth) && order.getIsPaid() == 1) {
                filteredOrders.add(order);
            }
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM", Locale.US);
        // Tạo danh sách tổng doanh thu theo tháng
        Map<String, Float> monthlyRevenueMap = new HashMap<>();
        for (Order order : filteredOrders) {
            String formattedMonth = sdf2.format(new Date(order.getOrderDate()));

            // Thêm doanh thu vào danh sách, nếu tháng đã có tồn tại, cộng thêm vào giá trị hiện tại
            monthlyRevenueMap.put(formattedMonth, monthlyRevenueMap.getOrDefault(formattedMonth, 0.0F) + order.getTotal());
        }

        List<Map.Entry<String, Float>> sortedList = new ArrayList<>(monthlyRevenueMap.entrySet());

        // Sắp xếp danh sách theo giá trị của key (tháng)
        Collections.sort(sortedList, Comparator.comparing(Map.Entry::getKey));
        if(sortedList.isEmpty()){
            return null;
        }
        return sortedList;


    }

    @Override
    public List<Map.Entry<String, Float>> extractAndSummarizeYearlyRevenue(String year) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY", Locale.US);
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        List<Order> orderList = orderRepository.findAllByStoreId(store.getId());
        // Lọc danh sách đơn hàng trong khoảng thời gian
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orderList) {
            String orderYear = sdf.format(new Date(order.getOrderDate()));
            if (orderYear.equalsIgnoreCase(year) && order.getIsPaid() == 1 ) {
                filteredOrders.add(order);
            }
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM", Locale.US);
        // Tạo danh sách tổng doanh thu theo tháng
        Map<String, Float> yearlyRevenueMap = new HashMap<>();
        for (Order order : filteredOrders) {
            String formattedMonth = sdf2.format(new Date(order.getOrderDate()));

            // Thêm doanh thu vào danh sách, nếu tháng đã có tồn tại, cộng thêm vào giá trị hiện tại
            yearlyRevenueMap.put(formattedMonth, yearlyRevenueMap.getOrDefault(formattedMonth, 0.0F) + order.getTotal());
        }

        List<Map.Entry<String, Float>> sortedList = new ArrayList<>(yearlyRevenueMap.entrySet());

        // Sắp xếp danh sách theo giá trị của key (tháng)
        Collections.sort(sortedList, Comparator.comparing(Map.Entry::getKey));
        if(sortedList.isEmpty()){
            return null;
        }

        return sortedList;
    }

    @Override
    public DashboardResponse getTotalOfAMonth() {
        float totalRevenue = 0.0F ;
        float avgOrder = 0.0F;
        float totalOrder = 0;
        Long  finishedOrder = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM", Locale.US);
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        String targetMonth = getCurrentMonth();
        List<Order> orderList = orderRepository.findAllByStoreId(store.getId());


        for (Order order : orderList) {
            String orderMonth = sdf.format(new Date(order.getOrderDate()));
            if (orderMonth.equalsIgnoreCase(targetMonth)) {
                totalOrder++;
            }
            if (orderMonth.equalsIgnoreCase(targetMonth) && order.getIsPaid() == 1 && order.getStatus() == 7) {
                finishedOrder++;
                totalRevenue = totalRevenue + order.getTotal() ;
            }

        }


        avgOrder = totalOrder / 30 ;

        return DashboardResponse.builder().avgOrder(avgOrder).finishedOrder(finishedOrder).revenue(totalRevenue).build();
    }


    private String getCurrentMonth(){

        LocalDate currentDate = LocalDate.now();

        // Lấy tháng từ ngày hiện tại
        Month currentMonth = currentDate.getMonth();

        // Chuyển đổi tháng thành kiểu String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedMonth = currentDate.format(formatter);


        return formattedMonth;
    }

}
