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
}