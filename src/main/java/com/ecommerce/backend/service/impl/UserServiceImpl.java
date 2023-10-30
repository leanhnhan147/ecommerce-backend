package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.option.OptionAdminDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.form.user.CreateUserForm;
import com.ecommerce.backend.form.user.UpdateUserForm;
import com.ecommerce.backend.mapper.UserMapper;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.UserService;
import com.ecommerce.backend.storage.criteria.UserCriteria;
import com.ecommerce.backend.storage.entity.Option;
import com.ecommerce.backend.storage.entity.Role;
import com.ecommerce.backend.storage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserAdminDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user"));
        return userMapper.fromEntityToUserAdminDto(user);
    }

    @Override
    public ResponseListDto<List<UserAdminDto>> getUserList(UserCriteria userCriteria, Pageable pageable) {
        Page<User> users = userRepository.findAll(userCriteria.getCriteria(), pageable);
        ResponseListDto<List<UserAdminDto>> responseListDto = new ResponseListDto<>();
        responseListDto.setContent(userMapper.fromEntityListToUserAdminDtoList(users.getContent()));
        responseListDto.setPage(pageable.getPageNumber());
        responseListDto.setTotalPages(users.getTotalPages());
        responseListDto.setTotalElements(users.getTotalElements());
        return responseListDto;
    }

    @Override
    public void createUser(CreateUserForm createUserForm) {
        Role role = roleRepository.findById(createUserForm.getRoleId()).orElse(null);
        if(role == null){
            throw new NotFoundException("Not found role");
        }
        User user = userMapper.fromCreateUserFormToEntity(createUserForm);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserForm updateUserForm) {
        User user = userRepository.findById(updateUserForm.getId()).orElse(null);
        if(user == null){
            throw new NotFoundException("Not found user");
        }
        userMapper.fromUpdateUserFormToEntity(updateUserForm, user);
        userRepository.save(user);
    }
}
