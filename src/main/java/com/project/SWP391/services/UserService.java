package com.project.SWP391.services;

import com.project.SWP391.responses.dto.UserInfoDTO;

import java.util.List;

public interface UserService {

    List<UserInfoDTO> getAllUsers(Long id);
    UserInfoDTO getUser(Long id);

    UserInfoDTO updateUserForAdmin(Long id, int status);

    UserInfoDTO updateUser(Long id, UserInfoDTO request);

    void deleteUser(Long id);

    UserInfoDTO getCurrentUser(Long id);


}
