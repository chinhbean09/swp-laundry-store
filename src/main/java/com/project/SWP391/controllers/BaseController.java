package com.project.SWP391.controllers;

import com.project.SWP391.requests.SpecialServiceFilterRequest;
import com.project.SWP391.responses.dto.*;
import com.project.SWP391.services.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/base")
@RequiredArgsConstructor
@Tag(name = "Base", description = "UnAuthorization APIs")
public class BaseController {

    private final StoreService storeService ;

    private final UserService userService;
    private final LaundryServiceImp laundryServiceImp;

    private final MaterialService materialService;

    private final ClothService clothService;

    private final OrderService orderService;

    private final FeedbackService feedbackService;

    private final StoreTimeService storeTimeService;

    private final TimeCategoryService timeCategoryService;


    @PostMapping("/store/filter")
    public ResponseEntity<List<StoreInfoDTO>> getAllStoresByFilter(@RequestBody SpecialServiceFilterRequest request){
        return ResponseEntity.ok(storeService.getAllStoreByFilter(request));
    }

    @GetMapping("/special-service/store/{id}")
    public ResponseEntity<List<LaundryInfoDTO>> getAllSpecialServiceByStore(@PathVariable Long id){
        return ResponseEntity.ok(laundryServiceImp.getAllSpecialServiceByStoreForCustomer(id) );
    }

    @GetMapping("/standard-service/store/{id}")
    public ResponseEntity<LaundryInfoDTO> getStandardServiceByStore(@PathVariable Long id){
        return ResponseEntity.ok(laundryServiceImp.getStandardServiceForCustomer(id));
    }

    @GetMapping("/special-service/all")
    public ResponseEntity<List<LaundryInfoDTO>> getAllSpecialService(){
        return ResponseEntity.ok(laundryServiceImp.getAllSpecialServiceForCustomer());
    }

    @GetMapping("/standard-service/all")
    public ResponseEntity<List<LaundryInfoDTO>> getAllStandardService(){
        return ResponseEntity.ok(laundryServiceImp.getAllStandardServiceForCustomer());
    }


    @GetMapping("/service/{id}") 
    public ResponseEntity<LaundryInfoDTO> getService(@PathVariable("id") Long id){
        return ResponseEntity.ok(laundryServiceImp.getServiceCustomer(id));
    }

    @GetMapping("/standard-service/prices")
    //@PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<LaundryDetailInfoDTO>> getPricesStandardService(@RequestParam(name = "store") long id) {
        return ResponseEntity.ok(laundryServiceImp.getPricesOfStandardService(id));
    }

    @GetMapping("/store/all")
    public ResponseEntity<List<StoreInfoDTO>> getAllStores(){
        return ResponseEntity.ok(storeService.getAllStore());
    }

    @GetMapping("/store/get/{id}")
    public ResponseEntity<StoreInfoDTO> getAStore(@PathVariable("id") Long id){
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping("/material/all")
    public ResponseEntity<List<MaterialDTO>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @GetMapping("/cloth/all")
    public ResponseEntity<List<ClothDTO>> getAllClothes() {
        return ResponseEntity.ok(clothService.getAllCloth());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserInfoDTO> getProfile(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(userService.getCurrentUser(id));
    }


    @GetMapping("/staff/accepted-order")
    public ResponseEntity<List<OrderInfoDTO>> getOrderByStaff() {
        return ResponseEntity.ok(orderService.getAllAcceptedOrdersByStaff());
    }

    @GetMapping("/staff/shipping-order")
    public ResponseEntity<List<OrderInfoDTO>> getDeliveryOrderByStaff() {
        return ResponseEntity.ok(orderService.getAllDeliveryOrdersByStaff());
    }
//    @PutMapping("/order/item/update/{id}")
//    //@PreAuthorize("hasAuthority('store:update')")
//    public ResponseEntity<ItemInfoDTO> updateItem(@PathVariable("id") Long id, @RequestParam("weight") Float weight){
//        return ResponseEntity.ok(orderService.updateItemOfAnOrder(id,weight));
//    }

    @PutMapping("/order/update/{id}")
    //@PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<OrderInfoDTO> updateAnOrder(@PathVariable(name = "id") Long id, @RequestParam(name = "status") int request){
        return ResponseEntity.ok(orderService.updateAnOderForStaff(id, request));
    }

    @GetMapping("/order/{id}")
   // @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<OrderInfoDTO> getAnOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getAnOderForStaff(id));
    }

    @GetMapping("laundry/feedback/{id}")
    // @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbackOfService(@PathVariable("id") Long id){
        return ResponseEntity.ok(feedbackService.getAllFeedbackOfService(id));
    }

    @GetMapping("store-time/{id}")
    // @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<List<StoreTimeDTO>> getAllStoreTimeOfService(@PathVariable("id") Long id){
        return ResponseEntity.ok(storeTimeService.getAllStoreTimeForCustomer(id));
    }

    @GetMapping("time")
    // @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<List<TimeDTO>> getAllTimeOfService(){
        List<TimeDTO> list = timeCategoryService.getAll();
        Predicate<TimeDTO> byStatus = timeDTO -> timeDTO.getStatus() == 1;
        return ResponseEntity.ok(list.stream().filter(byStatus).collect(Collectors.toList()));
    }

}
