package com.project.SWP391.controllers;

import com.project.SWP391.requests.SpecialServiceRequest;
import com.project.SWP391.requests.StandardServiceRequest;
import com.project.SWP391.requests.StoreRegisterRequest;
import com.project.SWP391.responses.DashboardResponse;
import com.project.SWP391.responses.SpecialServiceResponseInItem;
import com.project.SWP391.responses.dto.*;
import com.project.SWP391.services.LaundryServiceImp;
import com.project.SWP391.services.StoreService;
import com.project.SWP391.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/store")
@PreAuthorize("hasRole('STORE')")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Store", description = "Store management APIs")
public class StoreController {
    private final LaundryServiceImp service;
    private final StoreService storeService;
    private final UserService userService;

    private  final OrderService orderService;
    private final DashboardService dashboardService;

    private final StoreTimeService storeTimeService;

    private final CloudinaryService cloudinaryService;
//    @GetMapping("/profile")
//  @PreAuthorize("hasAuthority('store:read')")
//    public ResponseEntity<UserInfoDTO> getProfile() {
//        return ResponseEntity.ok(userService.getCurrentUser());
//    }

    @PutMapping("/profile/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<UserInfoDTO> updateProfile(@PathVariable Long id, @RequestBody UserInfoDTO request) {
        return ResponseEntity.ok(userService.updateUser(id,request));
    }

   @GetMapping("/special-service/all")
   @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<LaundryInfoDTO>> getAllSpecialServices(@RequestParam(name="store")  long id){
        return ResponseEntity.ok(service.getAllSpecialServiceForStore(id));
    }

    @GetMapping("/special-service/{id}")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<SpecialServiceResponseInItem> getSpecialServicesById(@PathVariable(name = "id") long id)
    {
            LaundryInfoDTO data = service.getServiceForStore(id);
            List<Long> materials = new ArrayList<>();
            data.getMaterials().stream().map(materialDTO -> materials.add(materialDTO.getId())).collect(Collectors.toList());

            var special = SpecialServiceResponseInItem.builder()
                    .price(data.getDetails().get(0).getPrice())
                    .unit(data.getDetails().get(0).getUnit())
                    .description(data.getDescription())
                    .name(data.getName())
                    .materials(materials)
                    .cloth(data.getCloth().getId())
                    .id(data.getId()).build();
       return  ResponseEntity.ok(special);
    }

    @GetMapping("/standard-service/get")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<LaundryInfoDTO> getStandardServicesById(@RequestParam(name = "store") long id) {
        return  ResponseEntity.ok(service.getStandardServiceForStore(id));
    }


    @PostMapping("/special-service/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<LaundryInfoDTO> createSpecialService(@RequestBody SpecialServiceRequest request) {
        return ResponseEntity.ok(service.CreateSpecialServiceByStoreId(request));
    }

    @PostMapping("/standard-service/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<LaundryInfoDTO> createStandardService(@RequestBody StandardServiceRequest request) {
        return ResponseEntity.ok(service.createStandardService(request));
    }

    @PostMapping("/standard-service/prices/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<LaundryDetailInfoDTO> createPricesStandardService(@RequestBody LaundryDetailInfoDTO request) {
        return ResponseEntity.ok(service.createPricesOfStandardService(request));
    }


    @PutMapping("/special-service/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<LaundryInfoDTO> updateSpecialService(@RequestBody SpecialServiceRequest request,@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.updateSpecialService(request,id));
    }

    @DeleteMapping("/special-service/delete/{id}")
    @PreAuthorize("hasAuthority('store:delete')")
    public ResponseEntity deleteSpecialService(@PathVariable(name = "id") long id) {
        service.deleteService(id);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/standard-service/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<LaundryInfoDTO> updateStandardService(@RequestBody StandardServiceRequest request,@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.updateStandardService(request,id));
    }

    @PutMapping("/standard-service/prices/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<LaundryDetailInfoDTO> updateStandardService(@RequestBody LaundryDetailInfoDTO request, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(service.updatePricesOfStandardService(id, request));
    }

    @DeleteMapping("/standard-service/delete/{id}")
    @PreAuthorize("hasAuthority('store:delete')")
    public ResponseEntity deleteStandardService(@PathVariable(name = "id") long id) {
        service.deleteService(id);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/standard-service/prices")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<LaundryDetailInfoDTO>> getPricesStandardService(@RequestParam(name = "store") long id) {
        return ResponseEntity.ok(service.getPricesOfStandardService(id));
    }

    @DeleteMapping("/standard-service/prices/delete/{id}")
    @PreAuthorize("hasAuthority('store:delete')")
    public ResponseEntity deletePricesStandardService(@PathVariable(name = "id") long id) {
        service.deletePrice(id);
        return ResponseEntity.ok().build();

    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<StoreInfoDTO> createStore(@RequestBody StoreRegisterRequest request){
       return ResponseEntity.ok(storeService.createStore(request));
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<StoreInfoDTO> getStore(){
        return ResponseEntity.ok(storeService.getCurrentStore());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<StoreInfoDTO> updateStore(@RequestBody StoreInfoDTO request){
        return ResponseEntity.ok(storeService.updateStore(request));
    }



    @GetMapping("/order/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<OrderInfoDTO> getAnOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getAnOder(id));
    }

    @GetMapping("/order/all/{id}")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<OrderInfoDTO>> getAllOrder(@PathVariable(name="id") long id){
        return ResponseEntity.ok(orderService.getAllOrdersByStore(id));
    }

    @GetMapping("/order/all/new")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<OrderInfoDTO>> getAllNewsOrder(){
        return ResponseEntity.ok(orderService.getAllNewOrders());
    }



    @PutMapping("/order/item/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<ItemInfoDTO> updateItem(@PathVariable("id") Long id, @RequestParam("weight") Float weight){
        return ResponseEntity.ok(orderService.updateItemOfAnOrder(id,weight));
    }

    @PutMapping("/order/update/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<OrderInfoDTO> updateAnOrder(@PathVariable(name = "id") long id, @RequestParam(name = "status") int request){
        return ResponseEntity.ok(orderService.updateAnOrder(id, request));
    }
    @GetMapping("dashboard/dow")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<Map.Entry<String, Float>>> getDashboardOfWeek(@RequestParam("target") Long date){
        return ResponseEntity.ok(dashboardService.getRevenueOfWeekFromSpecificDate(date));
    }

    @GetMapping("dashboard/month")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<Map.Entry<String, Float>>> getDashboardOfMonth(@RequestParam("target") String month){
        return ResponseEntity.ok(dashboardService.extractAndSummarizeMonthlyRevenue(month));
    }

    @GetMapping("dashboard/year")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<Map.Entry<String, Float>>> getDashboardOfYear(@RequestParam("target") String year){
        return ResponseEntity.ok(dashboardService.extractAndSummarizeYearlyRevenue(year));
    }


    @GetMapping("dashboard")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<DashboardResponse> getDataOfMonth(){
        return ResponseEntity.ok(dashboardService.getTotalOfAMonth());
    }


    @GetMapping("store-time")
    @PreAuthorize("hasAuthority('store:read')")
    public ResponseEntity<List<StoreTimeDTO>> getStoreTime(){
        return ResponseEntity.ok(storeTimeService.getAllStoreTime());
    }


    @PostMapping("store-time/create")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<StoreTimeDTO> createStoreTime(@RequestBody StoreTimeRequest request){
        return ResponseEntity.ok(storeTimeService.createStoreTime(request));
    }

    @PutMapping("store-time/update/{id}")
    @PreAuthorize("hasAuthority('store:create')")
    public ResponseEntity<StoreTimeDTO> updateStoreTime(@PathVariable(name="id") Long id, @RequestBody
                                                        UpdateStoreTimeRequest request
          ){
        return ResponseEntity.ok(storeTimeService.updateStoreTime(id, request.getPrice(), request.getDateRange()));
    }

    @PutMapping("store-time/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity disableStoreTime(@PathVariable(name="id") Long id, @RequestParam int status) {
        storeTimeService.setStatusOfStoreTime(id, status);
        return ResponseEntity.ok().build();
    }


    @PostMapping("image/upload/{id}")
    @PreAuthorize("hasAuthority('store:update')")
    public ResponseEntity<String> uploadImage(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id") long id){
        String url = cloudinaryService.uploadFile(file,id);

        return ResponseEntity.ok(url);
    }


}
