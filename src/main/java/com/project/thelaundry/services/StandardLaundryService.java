package com.project.thelaundry.services;


import com.project.thelaundry.entities.Laundry;
import com.project.thelaundry.entities.LaundryDetail;
import com.project.thelaundry.repositories.*;
import com.project.thelaundry.requests.SpecialServiceRequest;
import com.project.thelaundry.requests.StandardServiceRequest;
import com.project.thelaundry.responses.dto.LaundryInfoDTO;
import com.project.thelaundry.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandardLaundryService {
    private  final StoreRepository storeRepository;
    private  final LaundryServiceRepository serviceRepository;
    private final ModelMapper mapper;
    private  final ClotheRepository clotheRepository;
    private  final MaterialRepository materialRepository;
    private final LaundryDetailRepository laundryDetailRepository;

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
    private LaundryInfoDTO mapToDTO(Laundry dto) {
        return mapper.map(dto, LaundryInfoDTO.class);
    }
//    @Autowired
//    private final ModelMapper mapper;
//    public List<StandardServiceInfoDTO> getAllStandardServiceForCustomer(){
//
//        List<StandardLaundry> laundries = serviceRepository.findAll();
//        Predicate<StandardLaundry> byDeleted = specialLaundry -> specialLaundry.getIsDeleted() == 0;
//        return  laundries.stream().filter(byDeleted).map(service -> mapToDTO(service)).collect(Collectors.toList());
//
//    }
//
//    public StandardServiceInfoDTO getStandardServiceForCustomer(Long id){
//        StandardLaundry laundries = serviceRepository.findById(id).orElseThrow();
//        return  mapToDTO(laundries);
//
//    }
//
//    public StandardServiceInfoDTO getStandardServiceForStore(){
//
//        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
//        var laundry = serviceRepository.findByStoreId(store.getId());
//        return  mapToDTO(laundry);
//
//    }

//    public StandardServiceInfoDTO createStandardService(StandardServiceRequest request){
//        var store = storeRepository.findStoreByUserId(SecurityUtils.getPrincipal().getId());
//        var prices = request.getPrices_weight().stream().map(priceInWeightDTO -> mapToEntity(priceInWeightDTO)).collect(Collectors.toSet());
//
//
//
//
//        var service = StandardLaundry.builder().store(store)
//                .name(request.getName())
//                .isDeleted(0)
//                .description(request.getDescription())
//                .prices_weight(prices)
//                .build();
//
//
//
//        var newService = serviceRepository.save(service);
//        Set<PriceBasedWeight> newPrices = service.getPrices_weight().stream().peek(priceBasedWeight -> priceBasedWeight.setStandardLaundry(service)).collect(Collectors.toSet());
//        var savePrice = priceRepository.saveAll(prices);
//        return mapToDTO(newService);
//    }

//    public StandardServiceInfoDTO updateStandardService(StandardServiceRequest request, long id) {
//        var editStandardService = serviceRepository.findById(id).orElseThrow();
//        Set<PriceInWeightDTO> newPrices = request.getPrices_weight();
//        var prices = priceRepository.findAllByStandardLaundryId(editStandardService.getId());
//        for (var item: prices
//             ) {
//            for (var newItem: newPrices
//                 ) {
//                if(item.getId() == newItem.getId()){
//                    item.setPrice(newItem.getPrice());
//                    item.setFrom(newItem.getFrom());
//                    item.setTo(newItem.getTo());
//                }
//
//            }
//
//        }
//        if(editStandardService.getIsDeleted() == 1){
//            throw new RuntimeException("Service is not found");
//        }
//        editStandardService.setName(request.getName());
//        editStandardService.setDescription(request.getDescription());
//        var newService = serviceRepository.save(editStandardService);
//        var savePrice = priceRepository.saveAll(prices);
//        return  mapToDTO(newService);
//
//    }


//
//    public StandardServiceInfoDTO deleteSpecialService(long id) {
//        var editSpecialService = serviceRepository.findById(id).orElseThrow();
//
//        editSpecialService.setIsDeleted(1);
//
//        var newService = serviceRepository.save(editSpecialService);
//        return  mapToDTO(newService);
//
//    }
//
//
//    private StandardServiceInfoDTO mapToDTO(StandardLaundry dto) {
//        return mapper.map(dto, StandardServiceInfoDTO.class);
//    }
//
//    private PriceBasedWeight mapToEntity(PriceInWeightDTO dto){
//        return mapper.map(dto, PriceBasedWeight.class);
//    }

}
