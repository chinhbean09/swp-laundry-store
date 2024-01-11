package com.project.SWP391.services.ServiceImp;


import com.project.SWP391.entities.User;
import com.project.SWP391.repositories.UserRepository;
import com.project.SWP391.responses.dto.UserInfoDTO;
import com.project.SWP391.security.utils.SecurityUtils;
import com.project.SWP391.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {


    private final UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<UserInfoDTO> getAllUsers() {
        var users = userRepository.findAll();
        Predicate<User> byDeleted = user-> user.getStatus() != 2;
        return users.stream().filter(byDeleted).map(user -> mapToDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserInfoDTO getUser(Long id) {
        var user = userRepository.findById(id).orElseThrow();
        return mapToDTO(user);
    }

    @Override
    public UserInfoDTO updateUserForAdmin(Long id, int status) {
        var user = userRepository.findById(id).orElseThrow();
        user.setStatus(status);
        var newUser = userRepository.save(user);
        return mapToDTO(newUser);
    }

    @Override
    public UserInfoDTO updateUser(Long id, UserInfoDTO request) {
        var user = userRepository.findById(id).orElseThrow();
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setImage(request.getImage());
        user.setFullName(request.getFullName());
        var newUser = userRepository.save(user);
        return mapToDTO(newUser);
    }



    @Override
    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow();
        user.setStatus(3);
        var newUser = userRepository.save(user);

    }

    @Override
    public UserInfoDTO getCurrentUser() {
        var user = userRepository.findById(SecurityUtils.getPrincipal().getId()).orElseThrow();
        return mapToDTO(user);
    }

    private UserInfoDTO mapToDTO(User dto) {
        return mapper.map(dto, UserInfoDTO.class);
    }

}
