package com.yeom.pass.service.user;

import com.yeom.pass.repository.user.UserEntity;
import com.yeom.pass.repository.user.UserStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String userName;
    private UserStatus status;
    private String phone;

    public User toUser(UserEntity userEntity){
        this.userId = userEntity.getUserId();
        this.userName = userEntity.getUserName();
        this.status = userEntity.getStatus();
        this.phone = userEntity.getPhone();
        return this;
    }
}
