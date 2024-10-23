package com.backend.proyectointegradorc1g6.service.imp;
import com.backend.proyectointegradorc1g6.entity.Login;
import com.backend.proyectointegradorc1g6.repository.LoginRepository;
import com.backend.proyectointegradorc1g6.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final LoginRepository loginRepository;
    private int anInt;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public Login iniciarSession(Login login) {
        LOGGER.info("loginInput --> {}"+ anInt, login.toString());

        Login loginRegistrado = loginRepository.save(login);
        LOGGER.info("loginRegistrado --> {}", loginRegistrado.toString());

        return loginRegistrado;

    }
}
