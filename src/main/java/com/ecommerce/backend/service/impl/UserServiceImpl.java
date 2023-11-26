package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dto.ResponseListDto;
import com.ecommerce.backend.dto.user.UserAdminDto;
import com.ecommerce.backend.dto.user.UserDto;
import com.ecommerce.backend.exception.AlreadyExistsException;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.exception.BadRequestException;
import com.ecommerce.backend.form.login.LoginForm;
import com.ecommerce.backend.form.user.CreateUserForm;
import com.ecommerce.backend.form.user.UpdateProfileUserForm;
import com.ecommerce.backend.form.user.UpdateUserForm;
import com.ecommerce.backend.mapper.UserMapper;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.UserService;
import com.ecommerce.backend.storage.criteria.UserCriteria;
import com.ecommerce.backend.storage.entity.Role;
import com.ecommerce.backend.storage.entity.User;
import com.ecommerce.backend.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

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

        Optional<User> userByUsername = userRepository.findByUsername(createUserForm.getUsername());
        if(userByUsername.isPresent()){
            throw new AlreadyExistsException("Username already exist");
        }
        User userByPhone = userRepository.findByPhone(createUserForm.getPhone());
        if(userByPhone != null){
            throw new AlreadyExistsException("Phone already exist");
        }
        User userByEmail = userRepository.findByEmail(createUserForm.getEmail());
        if(userByEmail != null){
            throw new AlreadyExistsException("Email already exist");
        }

        User user = userMapper.fromCreateUserFormToEntity(createUserForm);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(createUserForm.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserForm updateUserForm) {
        User user = userRepository.findById(updateUserForm.getId()).orElse(null);
        if(user == null){
            throw new NotFoundException("Not found user");
        }
        if(!user.getPhone().equals(updateUserForm.getPhone())){
            User userByPhone = userRepository.findByPhone(updateUserForm.getPhone());
            if(userByPhone != null){
                throw new AlreadyExistsException("Phone already exist");
            }
        }
        if(!user.getEmail().equals(updateUserForm.getEmail())){
            User userByEmail = userRepository.findByEmail(updateUserForm.getEmail());
            if(userByEmail != null){
                throw new AlreadyExistsException("Email already exist");
            }
        }
        if (StringUtils.isNoneBlank(updateUserForm.getPassword())) {
            user.setPassword(passwordEncoder.encode(updateUserForm.getPassword()));
        }
        userMapper.fromUpdateUserFormToEntity(updateUserForm, user);
        userRepository.save(user);
    }

    @Override
    public UserDto login(LoginForm loginForm) {
        Optional<User> user = userRepository.findByUsername(loginForm.getUsername());
        if(user.isEmpty() || !passwordEncoder.matches((loginForm.getPassword()), user.get().getPassword())){
            throw new BadRequestException("Username or password is invalid");
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        String token = JwtUtils.generateToken(loginForm.getUsername());
        UserDto userDto = userMapper.fromEntityToLoginDto(user.get());
        userDto.setAccessToken(token);
        return userDto;
    }

    @Override
    public UserDto getProfile(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("Not found user");
        }
        return userMapper.fromEntityToUserProfileDto(user);
    }

    @Override
    public void updateProfile(Long id, UpdateProfileUserForm updateProfileUserForm) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new NotFoundException("Not found user");
        }
        if(!passwordEncoder.matches(updateProfileUserForm.getOldPassword(), user.getPassword())){
            throw new BadRequestException("Old password is incorrect");
        }
        if (StringUtils.isNoneBlank(updateProfileUserForm.getNewPassword())) {
            if(!updateProfileUserForm.getNewPassword().equals(updateProfileUserForm.getConfirmNewPassword())){
                throw new BadRequestException("Confirm new password mismatches");
            }
            user.setPassword(passwordEncoder.encode(updateProfileUserForm.getNewPassword()));
        }
        userMapper.fromUpdateProfileUserFormToEntity(updateProfileUserForm, user);
        userRepository.save(user);
    }
}
