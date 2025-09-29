package com.digis01.Securityandjwt.DAO;

import com.digis01.Securityandjwt.JPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUsuario extends JpaRepository<Usuario, Long>{

    Usuario findByUserName(String UserName);
}
