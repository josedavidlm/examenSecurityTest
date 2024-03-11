package com.codigo.examen.service;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.service.impl.RolServiceImpl;
import com.codigo.examen.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RolServiceTests {


    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    //@Autowired
    private RolServiceImpl rolService;

    private Rol rol = new Rol();


    @BeforeEach
    void setup(){
        rol.setIdRol(1L);
        rol.setNombreRol("Admin");
    }


    @Test
    @Order(1)
    void testCrearRolCaso1(){

        given(rolRepository.findByNombreRol(rol.getNombreRol())).willReturn(Optional.empty());
        given(rolRepository.save(rol)).willReturn(rol);
        var rol1 = rolService.createRol(rol);
        assertThat(rol1).isNotNull();
    }

    @Test
    @Order(2)
    void testCrearRolCaso2(){

        given(rolRepository.findByNombreRol(rol.getNombreRol())).willReturn(Optional.of(rol));
        var rol1 = rolService.createRol(rol);
        assertThat(rol1).isNotNull();
    }

    @Test
    @Order(3)
    void testObtenerUsuario(){
        given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.of(rol));
        var rol1 = rolService.getRolById(rol.getIdRol());
        assertThat(rol1).isNotNull();
    }

    @Test
    @Order(4)
    void testEliminarUsuarioCaso1(){
        given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.empty());
        var rol1 = rolService.deleteRol(rol.getIdRol());
        assertThat(rol1).isNotNull();
    }

    @Test
    @Order(5)
    void testEliminarUsuarioCaso2(){
        given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.of(rol));
        var rol1 = rolService.deleteRol(rol.getIdRol());
        assertThat(rol1).isNotNull();
    }
/*
    @Test
    @Order(6)
    void testEliminarRol(){
        var rol1 = rolService.deleteRol(rol.getIdRol());
        assertThat(rol1).isNotNull();
    }
*/
}
