package com.project.SWP391.services.ServiceImp;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.project.SWP391.entities.*;
import com.project.SWP391.repositories.*;
import com.project.SWP391.requests.CreateOrderRequest;
import com.project.SWP391.responses.dto.ItemInfoDTO;
import com.project.SWP391.responses.dto.OrderInfoDTO;
import com.project.SWP391.security.utils.SecurityUtils;
import com.project.SWP391.services.OrderService;
import com.project.SWP391.services.PaypalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.project.SWP391.contraints.Contraints.*;


@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {


    private final OrderRepository orderRepository;

    private final PaypalService service;


    private final LaundryDetailRepository laundryDetailRepository;


    private final LaundryServiceRepository serviceRepository;


    private final ModelMapper mapper;

    private final ItemRepository itemRepository;


    private final StoreRepository storeRepository;


    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final StoreTimeRepository storeTimeRepository;
    @Override
    public OrderInfoDTO createOrder(CreateOrderRequest request) {
        var id = SecurityUtils.getPrincipal().getId();
        Time time = null;
        if(request.getStoreTimeId() != null){
            time = storeTimeRepository.findById(request.getStoreTimeId()).orElseThrow();
        }


        OffsetDateTime odt = OffsetDateTime.now() ;
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "uuuuMMddHHmmssSSSD" ) ;
        var user = userRepository.findById(id).orElseThrow();
        var store = storeRepository.findById(request.getStoreId()).orElseThrow();
        var order = Order.builder().orderDate(request.getCreateDate())
                .status(1)
                .store(store).total(request.getTotal())
                .orderCode("ORD"+odt.format(f))
                .time(time)
                .user(user)
                .build();
        orderRepository.save(order);
        var items = itemRepository.saveAll(request.getItems().stream()
                .map(itemReqDTO -> Item.builder()
                        .total(itemReqDTO.getPrice() * itemReqDTO.getQuantity())
                        .quantity(itemReqDTO.getQuantity())
                        .laundryService(serviceRepository.findById(itemReqDTO.getId()).orElseThrow())
                        .order(order)
                        .build())

                .collect(Collectors.toList()));
        var newOrder = orderRepository.findById(order.getId()).orElseThrow();
        return mapToDTO(newOrder);
    }

    @Override
    public List<OrderInfoDTO> getAllOrders(Long id) {
        var orders = orderRepository.findAllByUserId(id);

        List<OrderInfoDTO> list = orders.stream().map(order -> mapToDTO(order)).collect(Collectors.toList());


        return list.stream().peek(orderInfoDTO -> orderInfoDTO.setOrderDate(convertDate(Long.parseLong(orderInfoDTO.getOrderDate())))).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfoDTO> getAllOrdersByStore(Long id) {

        var store = storeRepository.findStoreByUserId(id);
        var orders = orderRepository.findAllByStoreId(store.getId());

        List<OrderInfoDTO> list = orders.stream().map(order -> mapToDTO(order)).collect(Collectors.toList());


        return list.stream().peek(orderInfoDTO -> orderInfoDTO.setOrderDate(convertDate(Long.parseLong(orderInfoDTO.getOrderDate())))).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfoDTO> getAllNewOrders() {
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        var orders = orderRepository.findAllByStoreId(store.getId());
        Predicate<Order> byNew = order ->  order.getStatus() == 1 ;
        List<OrderInfoDTO> list = orders.stream().filter(byNew).map(order -> mapToDTO(order)).collect(Collectors.toList());


        return list.stream()
                .peek(orderInfoDTO -> orderInfoDTO
                        .setOrderDate(convertDate(Long.parseLong(orderInfoDTO.getOrderDate())))).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfoDTO> getAllAcceptedOrdersByStaff() {

        var orders = orderRepository.findAll();
        Predicate<Order> byProcessing = order -> order.getStatus() == 2;
        List<OrderInfoDTO> list = orders.stream().filter(byProcessing).map(order -> mapToDTO(order)).collect(Collectors.toList());



        return list.stream().peek(orderInfoDTO -> orderInfoDTO.setOrderDate(convertDate(Long.parseLong(orderInfoDTO.getOrderDate())))).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfoDTO> getAllDeliveryOrdersByStaff() {

        var orders = orderRepository.findAll();
        Predicate<Order> byProcessing = order -> order.getStatus() > 2;
        List<OrderInfoDTO> list = orders.stream().filter(byProcessing).map(order -> mapToDTO(order)).collect(Collectors.toList());



        return list.stream().peek(orderInfoDTO -> orderInfoDTO.setOrderDate(convertDate(Long.parseLong(orderInfoDTO.getOrderDate())))).collect(Collectors.toList());
    }

    @Override
    public OrderInfoDTO getAnOder(Long id) {

        var user = SecurityUtils.getPrincipal();
        var order = orderRepository.findById(id).orElseThrow();
        if(user.getRole().equals(Role.USER)){
            if (order.getUser().getId() != user.getId()){
                throw new IllegalArgumentException();
            }
        }

        if(user.getRole().equals(Role.STORE)){
            var store = storeRepository.findStoreByUserId(user.getId());
            if(order.getStore().getId() != store.getId()){
                throw new IllegalArgumentException();
            }
        }


        float total = 0F;
        var items = itemRepository.findAllByOrderId(id);
        for (Item item: items
        ) {
            total += item.getTotal();

        }
        if(order.getTime() != null){
            order.setTotal(total + order.getTime().getPrice());
        }else{
            order.setTotal(total);
        }


        orderRepository.save(order);
        OrderInfoDTO dto = mapToDTO(order);
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(order.getOrderDate());
        dto.setOrderDate(obj.format(res));

        return dto;
    }
    @Override
    public OrderInfoDTO getAnOderForStaff(Long id) {


        var order = orderRepository.findById(id).orElseThrow();


        order.setTotal(0F);
        float total = 0F;
        var items = itemRepository.findAllByOrderId(id);
        for (Item item: items
        ) {
            total += item.getTotal();

        }
        order.setTotal(total);
        orderRepository.save(order);
        OrderInfoDTO dto = mapToDTO(order);
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(order.getOrderDate());
        dto.setOrderDate(obj.format(res));
        return dto;
    }

    @Override
    public void cancelAnOrder(Long id) {
        var user = SecurityUtils.getPrincipal();
        var order = orderRepository.findById(id).orElseThrow();
        if(user.getRole().equals(Role.USER)){
            if (order.getUser().getId() != user.getId()){
                throw new IllegalArgumentException();
            }
        }


        order.setStatus(0);
        orderRepository.save(order);
    }

    @Override
    public OrderInfoDTO updateAnOrder(Long id, int request) {
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());

        var order = orderRepository.findById(id).orElseThrow();



            if(store.getId() != order.getStore().getId() ){
                throw new IllegalArgumentException();
            }

        order.setStatus(request);

        var  update = orderRepository.save(order);
        OrderInfoDTO dto = mapToDTO(update);
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(update.getOrderDate());
        dto.setOrderDate(obj.format(res));
        return dto;
    }

    @Override
    public OrderInfoDTO updateAnOderForStaff(Long id, int request) {

        var order = orderRepository.findById(id).orElseThrow();



        order.setStatus(request);
        if(request == 7){
            order.setIsPaid(1);
            var payment = Payment.builder().order(order).method("DELIVERY")
                    .createDate(convertDate(System.currentTimeMillis())).build();

            paymentRepository.save(payment);
        }
        var  update = orderRepository.save(order);
        OrderInfoDTO dto = mapToDTO(update);
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(update.getOrderDate());
        dto.setOrderDate(obj.format(res));
        return dto;
    }


    @Override
    public ItemInfoDTO updateItemOfAnOrder(Long id, Float weight) {
        var item = itemRepository.findById(id).orElseThrow();
        item.setWeight(weight);
        var detail = laundryDetailRepository.findAllByLaundryServiceId(item.getLaundryService().getId());
        for (LaundryDetail dto: detail
             ) {
            if(weight >= dto.getFrom() &&  weight <= dto.getTo()){
                item.setTotal(weight * dto.getPrice());
            }

        }
        var update = itemRepository.save(item);
        return mapper.map(update, ItemInfoDTO.class);
    }

    @Override
    public String payAnOrder(Long id) {

        try {
            var order = orderRepository.findById(id).orElseThrow();
            if(order.getIsPaid() == 0 && order.getStatus() >= 5){
                double price = order.getTotal();
                Payment payment = service.createPayment(price,
                        "USD",METHOD,INTENT,
                        order.getOrderCode(),
                        CANCEL_URL+id,SUCCESS_URL+id);
                for(Links link:payment.getLinks()) {
                    if(link.getRel().equals("approval_url")) {
                        return link.getHref();
                    }
                }
            }else{
                throw new RuntimeException("Order was paid");
            }


        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "localhost:3000/";

    }

    @Override
    public OrderInfoDTO confirmSuccessPaypal(String payId, String payerId) {
        try {
            Payment payment = service.executePayment(payId, payerId);

            if (payment.getState().equals("approved")) {
                System.out.println(payment.toJSON());
              Transaction transaction = payment.getTransactions().get(0);
               var order = orderRepository.findByOrderCode(transaction.getDescription()).orElseThrow();
               order.setIsPaid(1);
               var paidOrder = orderRepository.save(order);
                var payments = Payment.builder().order(order).method("PAYPAL="+payerId)
                        .createDate(convertDate(System.currentTimeMillis())).build();

                paymentRepository.save(payments);
               return mapToDTO(paidOrder);
            }
        } catch (PayPalRESTException e) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    private OrderInfoDTO mapToDTO(Order dto){
        return mapper.map(dto, OrderInfoDTO.class);
    }

    private String convertDate (Long date){
        DateFormat obj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(date);
        return  obj.format(res);
    }
}
