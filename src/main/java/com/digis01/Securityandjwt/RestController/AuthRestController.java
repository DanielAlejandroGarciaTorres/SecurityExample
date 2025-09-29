package com.digis01.Securityandjwt.RestController;

import com.digis01.Securityandjwt.Component.JwtUtil;
import com.digis01.Securityandjwt.DAO.IUsuario;
import com.digis01.Securityandjwt.JPA.Rol;
import com.digis01.Securityandjwt.JPA.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthRestController {
    private final IUsuario iUsuario;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthRestController(IUsuario iUsuario, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.iUsuario = iUsuario;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/signup")
    public Usuario signup(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.Rol = new Rol();
        return iUsuario.save(usuario);
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario dbUser = iUsuario.findByUserName(usuario.getUserName());
        if (passwordEncoder.matches(usuario.getPassword(), dbUser.getPassword())) {
            return jwtUtil.generateToken(dbUser.getUserName(), dbUser.Rol.getNombre());
        }
        throw new RuntimeException("Credenciales inválidas");
    }
}
