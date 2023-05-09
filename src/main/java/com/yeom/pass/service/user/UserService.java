package com.yeom.pass.service.user;

import com.yeom.pass.repository.user.UserEntity;
import com.yeom.pass.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(final String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        return UserModelMapper.INSTANCE.toUser(userEntity);
    }

    public List<UserEntity> getUserList(){

        return userRepository.findAll();
    }
}
