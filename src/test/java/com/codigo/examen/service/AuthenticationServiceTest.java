package com.codigo.examen.service;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.request.SignUpRequest;
import com.codigo.examen.service.impl.AuthenticationServiceImpl;
import com.codigo.examen.util.Role;
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
public class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    private Usuario usuario = new Usuario();

    private Rol rol = new Rol();

    Set<Rol> roles = new HashSet<>();

    private SignUpRequest signUpRequest = new SignUpRequest();
    private SignInRequest signInRequest = new SignInRequest();



    @BeforeEach
    void setup(){
        rol.setIdRol(1L);
        rol.setNombreRol(Role.ADMIN.name());
        roles.add(rol);
        usuario.setIdUsuario(1L);
        usuario.setUsername("mldesoj");
        usuario.setEmail("mldesoj@codigo.com.pe");
        usuario.setTelefono("965511350");
        usuario.setEnabled(true);
        usuario.setAccountnonexpire(false);
        usuario.setAccountnonlocked(false);
        usuario.setCredentialsnonexpired(false);
        usuario.setPassword("pwdpoiñlkmnb");
        usuario.setRoles(roles);
    }


    @Test
    @Order(1)
    void testsignUpUser(){
        signUpRequest.setNombres("Jose");
        signUpRequest.setApellidos("Lagos");
        signUpRequest.setEmail("mldesoj@hotmail.com");
        signUpRequest.setUsername("mldesoj");
        signUpRequest.setPassword("poiñlkmnbv");

        //given(usuarioRepository.findByUsername(usuario.getUsername())).willReturn(Optional.empty());
        //given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.of(rol));
        given(usuarioRepository.save(usuario)).willReturn(usuario);
        var usuario1 = authenticationService.signUpUser(signUpRequest);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(2)
    void testsignUpAdmin(){
        signUpRequest.setNombres("Javier");
        signUpRequest.setApellidos("Lagos");
        signUpRequest.setEmail("mldreivaj@hotmail.com");
        signUpRequest.setUsername("mldreivaj");
        signUpRequest.setPassword("qweasdzxcv");

        //given(usuarioRepository.findByUsername(usuario.getUsername())).willReturn(Optional.empty());
        //given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.of(rol));
        given(usuarioRepository.save(usuario)).willReturn(usuario);
        var usuario1 = authenticationService.signUpAdmin(signUpRequest);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(3)
    void testsignin(){
        signInRequest.setUsername("mldesoj");
        signInRequest.setPassword("pwdpoiñlkmnb");
        given(usuarioRepository.findByUsername(usuario.getUsername())).willReturn(Optional.of(usuario));
        var usuario1 = authenticationService.signin(signInRequest);
        assertThat(usuario1).isNotNull();
    }
}
