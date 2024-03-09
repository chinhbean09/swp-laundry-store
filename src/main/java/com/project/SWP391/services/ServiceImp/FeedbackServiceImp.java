package com.project.SWP391.services.ServiceImp;

import com.project.SWP391.entities.Feedback;
import com.project.SWP391.entities.Role;
import com.project.SWP391.repositories.FeedbackRepository;
import com.project.SWP391.repositories.LaundryServiceRepository;
import com.project.SWP391.repositories.UserRepository;
import com.project.SWP391.requests.FeedbackRequest;
import com.project.SWP391.responses.dto.FeedbackDTO;
import com.project.SWP391.security.utils.SecurityUtils;
import com.project.SWP391.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FeedbackServiceImp implements FeedbackService {
    private final LaundryServiceRepository serviceRepository;
    private final UserRepository userRepository;

    private  final ModelMapper mapper;
    private final FeedbackRepository feedbackRepository;
    @Override
    public FeedbackDTO createFeedback(FeedbackRequest request) {
        var user = userRepository.findById(SecurityUtils.getPrincipal().getId()).orElseThrow();
        if(!user.getRole().equals(Role.USER)){
            throw new IllegalArgumentException();
        }
        var service = serviceRepository.findById(request.getServiceId()).orElseThrow();
        var feedback = Feedback.builder().user(user).content(request.getContent())
                .star(request.getStar()).laundryService(service).build();
        var newFeedback = feedbackRepository.save(feedback);

        return mapToDTO(newFeedback);
    }

    @Override
    public void deleteFeedback(Long id) {
        var user = userRepository.findById(SecurityUtils.getPrincipal().getId()).orElseThrow();
        if(!user.getRole().equals(Role.USER)){
            throw new IllegalArgumentException();
        }
        var feedback = feedbackRepository.findById(id).orElseThrow();
        if(user.getId() != feedback.getUser().getId()){
            throw new IllegalArgumentException();
        }


        feedbackRepository.deleteById(id);
    }

    @Override
    public List<FeedbackDTO> getAllFeedbackOfService(Long id) {
        List<Feedback> list = feedbackRepository.findAllByLaundryServiceId(id);


        return list.stream().map(feedback -> mapToDTO(feedback)).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDTO> getAllFeedbackOfUser() {
        List<Feedback> list = feedbackRepository.findAllByUserId(SecurityUtils.getPrincipal().getId());



        return list.stream().map(feedback -> mapToDTO(feedback)).collect(Collectors.toList());
    }

    private FeedbackDTO mapToDTO(Feedback dto){
        return mapper.map(dto, FeedbackDTO.class);
    }
}
