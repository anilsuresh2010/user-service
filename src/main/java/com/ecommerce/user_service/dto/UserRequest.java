package com.ecommerce.user_service.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private double balance;
}
