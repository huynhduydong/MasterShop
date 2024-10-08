package com.dong.service;


import com.dong.dto.model.UserDto;
import com.dong.dto.response.ObjectResponse;

public interface UserService {
    UserDto createUser(UserDto userDto);
    ObjectResponse<UserDto> getAllUser(int pageSize, int pageNo, String sortBy, String sortDir);
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String userName);
    UserDto updateUser(UserDto userDto, Long userId);
    void deleteUser(Long userId);
    UserDto getUserProfile(Long userId);
    ObjectResponse<UserDto> searchUser(String name, int pageNo, int pageSize, String sortBy, String sortDir);
    public boolean deactivateUser(Long userId);
    public boolean activateUser(Long userId);

}