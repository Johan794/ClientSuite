package com.seek.TalentSuite.application.service;

import com.seek.TalentSuite.application.dto.request.LoginDto;
import com.seek.TalentSuite.application.dto.request.RegisterDto;
import com.seek.TalentSuite.application.dto.response.RegisteredDto;
import com.seek.TalentSuite.persistence.entity.User;


public interface AuthService {
    RegisteredDto signup(RegisterDto registerDto);
    User authenticate(LoginDto loginDto);
}
