package com.project.SWP391.controllers;

import com.project.SWP391.requests.SpecialServiceFilterRequest;
import com.project.SWP391.responses.dto.LaundryInfoDTO;
import com.project.SWP391.responses.dto.MaterialDTO;
import com.project.SWP391.responses.dto.StoreInfoDTO;
import com.project.SWP391.services.LaundryServiceImp;
import com.project.SWP391.services.MaterialService;
import com.project.SWP391.services.StoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/base")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Base", description = "UnAuthorization APIs")
public class BaseController {

    private final StoreService storeService ;
    private final LaundryServiceImp laundryServiceImp;

    private final MaterialService materialService;

    @GetMapping("/store/filter")
    public ResponseEntity<List<StoreInfoDTO>> getAllStoresByFilter(@RequestBody SpecialServiceFilterRequest request){
        return ResponseEntity.ok(storeService.getAllStoreByFilter(request));
    }

    @GetMapping("/special-service/store/{id}")
    public ResponseEntity<List<LaundryInfoDTO>> getAllSpecialServiceByStore(@PathVariable Long id){
        return ResponseEntity.ok(laundryServiceImp.getAllSpecialServiceByStoreForCustomer(id) );
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
    public ResponseEntity<LaundryInfoDTO> getService(@PathVariable Long id){
        return ResponseEntity.ok(laundryServiceImp.getServiceCustomer(id));
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



}
