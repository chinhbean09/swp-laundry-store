package com.project.SWP391.services;

import com.project.SWP391.entities.Laundry;
import com.project.SWP391.entities.LaundryDetail;
import com.project.SWP391.repositories.*;
import com.project.SWP391.requests.SpecialServiceRequest;
import com.project.SWP391.requests.StandardServiceRequest;
import com.project.SWP391.responses.dto.LaundryDetailInfoDTO;
import com.project.SWP391.responses.dto.LaundryInfoDTO;
import com.project.SWP391.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class LaundryServiceImp{

    private  final LaundryServiceRepository serviceRepository;

    private final LaundryDetailRepository laundryDetailRepository;
    private  final StoreRepository storeRepository;

    private  final ClotheRepository clotheRepository;

    private  final MaterialRepository materialRepository;
    @Autowired
    private final ModelMapper mapper;








    public List<LaundryInfoDTO> getAllSpecialServiceForStore(Long  id){
        var store = storeRepository.findStoreByUserId(id);
        List<Laundry> laundries = serviceRepository.findAllByStoreId(store.getId());
        Predicate<Laundry> byDeleted = laundry -> laundry.getIsDeleted() == 0;
        Predicate<Laundry> bySpecial = specialLaundry -> specialLaundry.getIsStandard().equals(false);
        return  laundries.stream().filter(byDeleted).filter(bySpecial).map(service -> mapToDTO(service)).collect(Collectors.toList());

    }
    public LaundryInfoDTO getServiceForStore(Long id){
        var laundry = serviceRepository.findById(id).orElseThrow();
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        if((laundry.getStore()).getId() != store.getId()){
            throw new RuntimeException("You don't have permission to access this action");
        }
        return mapToDTO(laundry);

    }

    public List<LaundryInfoDTO> getAllSpecialServiceByStoreForCustomer(Long id){
        List<Laundry> laundries = serviceRepository.findAllByStoreId(id);
        Predicate<Laundry> byDeleted = specialLaundry -> specialLaundry.getIsDeleted() == 0;
        Predicate<Laundry> bySpecial = specialLaundry -> specialLaundry.getIsStandard().equals(false);
        return  laundries.stream().filter(byDeleted).filter(bySpecial).map(service -> mapToDTO(service)).collect(Collectors.toList());

    }
    public List<LaundryInfoDTO> getAllStandardServiceForCustomer(){
        List<Laundry> laundries = serviceRepository.findAll();
        Predicate<Laundry> byDeleted = specialLaundry -> specialLaundry.getIsDeleted() == 0;
        Predicate<Laundry> bySpecial = specialLaundry -> specialLaundry.getIsStandard().equals(true);
        return  laundries.stream().filter(byDeleted).filter(bySpecial).map(service -> mapToDTO(service)).collect(Collectors.toList());

    }

    public List<LaundryInfoDTO> getAllSpecialServiceForCustomer(){

        List<Laundry> laundries = serviceRepository.findAll();
        Predicate<Laundry> byDeleted = specialLaundry -> specialLaundry.getIsDeleted() == 0;
        Predicate<Laundry> bySpecial = specialLaundry -> specialLaundry.getIsStandard().equals(false);
        return  laundries.stream().filter(byDeleted).filter(bySpecial).map(service -> mapToDTO(service)).collect(Collectors.toList());

    }

    public LaundryInfoDTO getServiceCustomer(Long id){
        var laundry = serviceRepository.findById(id).orElseThrow();
        return mapToDTO(laundry);

    }

    public LaundryInfoDTO CreateSpecialServiceByStoreId(SpecialServiceRequest request){


        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        var cloth = clotheRepository.findById(request.getCloth()).orElseThrow();
        var material = materialRepository.findAllById(request.getMaterials()).stream().collect(Collectors.toList());


        var laundry = Laundry.builder().name(request.getName())
                .store(store)
                .materials(material)
                .cloth(cloth)
                .description(request.getDescription())
                .imageBanner(request.getImageBanner())
                .isDeleted(0)
                .isStandard(false)
                .build();
        var newService = serviceRepository.save(laundry);
        var savedLaundry = serviceRepository.findById(newService.getId()).orElseThrow();
        var detail = LaundryDetail.builder()
                .laundryService(savedLaundry)
                .price(request.getPrice())
                .unit(request.getUnit())
                .build();
        var savedDetail = laundryDetailRepository.save(detail);
        return  mapToDTO(newService);

    }


    private LaundryInfoDTO mapToDTO(Laundry dto) {
        return mapper.map(dto, LaundryInfoDTO.class);
    }

    private LaundryDetail mapToEntity(LaundryDetailInfoDTO dto){
        return mapper.map(dto, LaundryDetail.class);
    }

    public LaundryInfoDTO updateSpecialService(SpecialServiceRequest request, long id) {
        var editSpecialService = serviceRepository.findById(id).orElseThrow();
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        if(editSpecialService.getStore().getId() != store.getId() ){
            throw new RuntimeException("Request is denied");
        }

        var cloth = clotheRepository.findById(request.getCloth()).orElseThrow();
        var material = materialRepository.findAllById(request.getMaterials()).stream().collect(Collectors.toList());

        if(editSpecialService.getIsDeleted() == 1){
           throw new RuntimeException("Service is not found");
        }

        editSpecialService.setName(request.getName());
        editSpecialService.setDescription(request.getDescription());
        editSpecialService.setMaterials(material);
        editSpecialService.setImageBanner(request.getImageBanner());
        editSpecialService.setCloth(cloth);
        var editDetail = laundryDetailRepository.findByLaundryServiceId(editSpecialService.getId());
        editDetail.setPrice(request.getPrice());
        editDetail.setUnit(request.getUnit());
        var newDetail = laundryDetailRepository.save(editDetail);
        var newService = serviceRepository.save(editSpecialService);
        return  mapToDTO(newService);

    }

    public void deleteService(long id) {
        var editSpecialService = serviceRepository.findById(id).orElseThrow();
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        if(editSpecialService.getStore().getId() != store.getId() ){
            throw new RuntimeException("Request is denied");
        }

        editSpecialService.setIsDeleted(1);

        var newService = serviceRepository.save(editSpecialService);


    }

    public LaundryInfoDTO createStandardService(StandardServiceRequest request){
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        //var prices = request.getDetails().stream().map(priceInWeightDTO -> mapToEntity(priceInWeightDTO)).collect(Collectors.toSet());




        var service = Laundry.builder().store(store)
                .name(request.getName())
                .isDeleted(0)
                .isStandard(true)
                .description(request.getDescription())
                .imageBanner(request.getImageBanner())
                .build();



        var newService = serviceRepository.save(service);
//        List<LaundryDetail> newPrices = service.getDetails().stream().peek(priceBasedWeight -> priceBasedWeight.setLaundryService(newService)).collect(Collectors.toList());
//        var savePrice = laundryDetailRepository.saveAll(newPrices);
        return mapToDTO(newService);
    }

    public LaundryDetailInfoDTO createPricesOfStandardService(LaundryDetailInfoDTO newPrice){
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        var laundry = serviceRepository.findByStoreIdAndIsStandardTrue(store.getId());
        var price = LaundryDetail.builder().price(newPrice.getPrice())
                .from(newPrice.getFrom())
                .to(newPrice.getTo())
                .laundryService(laundry)
                .unit(newPrice.getUnit())
                .build();

        var newDetails = laundryDetailRepository.save(price);

        return mapper.map(price, LaundryDetailInfoDTO.class);
    }

    public LaundryDetailInfoDTO getPricesOfStandardService(Long id){
        var price = laundryDetailRepository.findById(id).orElseThrow();
        return mapper.map(price, LaundryDetailInfoDTO.class);
    }

    public List<LaundryDetailInfoDTO> getPricesOfStandardService(long id){
        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
        var laundry = serviceRepository.findByStoreIdAndIsStandardTrue(store.getId());
        List<LaundryDetail> price = laundryDetailRepository.findAllByLaundryServiceId(laundry.getId());
        return price.stream().map( laundryDetail ->mapper.map(laundryDetail, LaundryDetailInfoDTO.class)).collect(Collectors.toList());
    }

    public LaundryDetailInfoDTO updatePricesOfStandardService(Long id, LaundryDetailInfoDTO newPrice){
        var price = laundryDetailRepository.findById(id).orElseThrow();
        price.setPrice(newPrice.getPrice());
        price.setTo(newPrice.getTo());
        price.setFrom(newPrice.getFrom());
        price.setUnit(newPrice.getUnit());

        var updatedPrice = laundryDetailRepository.save(price);
        return mapper.map(updatedPrice, LaundryDetailInfoDTO.class);
    }


    public void deletePrice (long id){
        laundryDetailRepository.deleteById(id);
    }



    public LaundryInfoDTO updateStandardService(StandardServiceRequest request, long id) {
        var editStandardService = serviceRepository.findById(id).orElseThrow();
        if(editStandardService.getIsDeleted() == 1){
            throw new RuntimeException("Service is not found");
        }



        editStandardService.setName(request.getName());
        editStandardService.setDescription(request.getDescription());
        var newService = serviceRepository.save(editStandardService);

        return  mapToDTO(newService);

    }




    public LaundryInfoDTO getStandardServiceForStore(long id) {

        var store = storeRepository.findStoreByUserId(id);
        var laundry = serviceRepository.findByStoreIdAndIsStandardTrue(store.getId());
        return mapToDTO(laundry);
    }

    public LaundryInfoDTO getStandardServiceForCustomer(Long id) {


        var laundry = serviceRepository.findByStoreIdAndIsStandardTrue(id);
        return mapToDTO(laundry);
    }
}



