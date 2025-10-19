package com.lxp.api.dto;

import java.util.regex.Pattern;

public record CreateUserRequest(
        String email, String password, String name, String introduction, String resume) {
    public void validate() {
        if (email == null || email.isEmpty()) throw new IllegalStateException("이메일은 필수 값입니다.");
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(EMAIL_REGEX, email)) throw new IllegalStateException("유효하지 않은 이메일 형식입니다.");

        if (password == null || password.isEmpty()) throw new IllegalStateException("비밀번호는 필수 값입니다.");
        final String PASSWORD_REGEX =
                "^(?=.*[!@#$%^&*(),.?\":{}|<>\\[\\]\\/\\\\;'+\\-=~_`])(?=.*[A-Za-z0-9])[A-Za-z0-9!@#$%^&*(),.?\":{}|<>\\[\\]\\/\\\\;'+\\-=~_`]{8,}$";
        if (!Pattern.matches(PASSWORD_REGEX, password))
            throw new IllegalStateException("비밀번호는 영어와 숫자만 가능하며 특수 문자 최소 1개를 포함하여 8자리 이상이 되어야 합니다.");

        if (name == null || name.isEmpty()) throw new IllegalStateException("이름은 필수 값입니다.");
        if (name.length() > 8) throw new IllegalStateException("이름은 8자리 이상이 될 수 없습니다.");
    }
}
