package com.project.SWP391.services.ServiceImp;


import com.project.SWP391.entities.User;
import com.project.SWP391.repositories.UserRepository;
import com.project.SWP391.responses.dto.UserInfoDTO;
import com.project.SWP391.security.jwt.JwtService;
import com.project.SWP391.services.AuthenticationService;
import com.project.SWP391.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class    UserServiceImp implements UserService {


    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationService service;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<UserInfoDTO> getAllUsers(Long id) {
        var users = userRepository.findAll();
        Predicate<User> byDeleted = user-> user.getStatus() != 0;
        Predicate<User> byId = user-> user.getId() != id;
        return users.stream().filter(byDeleted).filter(byId).map(user -> mapToDTO(user)).collect(Collectors.toList());
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
        if(status == 2){
            service.revokeAllUserTokens(user);
        }

        if(status == 1){
            var jwtToken = jwtService.generateToken((UserDetails) user);
            service.saveUserToken(user, jwtToken);
        }
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
        user.setStatus(0);
        service.revokeAllUserTokens(user);
        var newUser = userRepository.save(user);

    }

    @Override
    public UserInfoDTO getCurrentUser(Long id) {
        var user = userRepository.findById(id).orElseThrow();
        return mapToDTO(user);
    }

    private UserInfoDTO mapToDTO(User dto) {
        return mapper.map(dto, UserInfoDTO.class);
    }

}
