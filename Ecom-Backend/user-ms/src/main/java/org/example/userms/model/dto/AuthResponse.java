package org.example.userms.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userms.model.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private boolean success;
    private String message;
    private String token;
    private User user;
    private String error;


}