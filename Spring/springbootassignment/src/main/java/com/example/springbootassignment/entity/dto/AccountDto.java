package com.example.springbootassignment.entity.dto;

import com.example.springbootassignment.entity.myenum.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String id;
    @NotEmpty(message = "username missing")
    private String username;
    @NotEmpty(message = "password missing")
    @Min(value = 8, message = "password not strong enough")
    private String password;
    @NotEmpty(message = "password repeat missing")
    private String rePass;
    @NotEmpty(message = "firstName missing")
    private String firstName;
    @NotEmpty(message = "lastName missing")
    private String lastName;
    @NotEmpty(message = "email missing")
    @Email(message = "email wrong")
    private String email;
    @NotEmpty(message = "phone missing")
    private String phone;
    @NotEmpty(message = "address missing")
    private String address;
    private String thumbnail;
    private String detail;
    @Enumerated(EnumType.ORDINAL)
    private AccountStatus status;
    private String role;
}
