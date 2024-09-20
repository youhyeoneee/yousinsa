package com.yhkim.yousinsa.domain.user.dto;


import com.yhkim.yousinsa.domain.user.entity.Grade;
import com.yhkim.yousinsa.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
public class SignupUserRequest {
    @NotBlank(message = "계정을 입력해주세요.")
    @Length(min = 5, max = 20, message = "계정은 5~20자여야 합니다.")
    @Pattern(regexp = "^[a-z0-9_-]+$",
            message = "계정에는 소문자, 숫자, 하이픈(-), 밑줄(_)만 사용할 수 있습니다.")
    private String username;
    
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "비밀번호는 8~16자여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
            message = "비밀번호에는 문자, 숫자, 특수문자(@!%?&)가 각각 1개 이상 포함되어야 합니다.")
    private String password;
    
    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
    
    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .grade(Grade.NONE)
                .build();
    }
}
