package com.yeom.pass.service.user;

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

}
