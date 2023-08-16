package com.example.crud_mysql_jwt;


import com.example.crud_mysql_jwt.models.Rol;
import com.example.crud_mysql_jwt.repositories.RolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudMysqlJwtApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CrudMysqlJwtApplication.class, args);
        RolRepository repository= context.getBean(RolRepository.class);
        repository.save(new Rol(1,"ROL_ADMIN"));
        repository.save(new Rol(2,"ROL_USER"));
    }

}
