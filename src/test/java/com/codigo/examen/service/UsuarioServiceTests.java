package com.codigo.examen.service;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.service.impl.RolServiceImpl;
import com.codigo.examen.service.impl.UsuarioServiceImpl;
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
public class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @InjectMocks
    private RolServiceImpl rolService;
    private Usuario usuario = new Usuario();
    private Usuario usuarioCaso2 = new Usuario();

    private Rol rol = new Rol();

    Set<Rol> roles = new HashSet<>();

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

        usuarioCaso2.setIdUsuario(2L);
        usuarioCaso2.setUsername("mldreivaj");
        usuarioCaso2.setEmail("mldreivaj@codigo.com.pe");
        usuarioCaso2.setTelefono("965511351");
        usuarioCaso2.setEnabled(true);
        usuarioCaso2.setAccountnonexpire(false);
        usuarioCaso2.setAccountnonlocked(false);
        usuarioCaso2.setCredentialsnonexpired(false);
        usuarioCaso2.setPassword("pwdpoiñlkmnb1");
        usuarioCaso2.setRoles(roles);

    }


    @Test
    @Order(1)
    void testCrearUsuarioCas01(){
        given(usuarioRepository.findByUsername(usuario.getUsername())).willReturn(Optional.empty());
        given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.of(rol));
        given(usuarioRepository.save(usuario)).willReturn(usuario);
        var usuario1 = usuarioService.createUsuario(usuario);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(2)
    void testCrearUsuarioCas02(){
        given(usuarioRepository.findByUsername(usuario.getUsername())).willReturn(Optional.of(usuario));
        var usuario1 = usuarioService.createUsuario(usuario);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(3)
    void testCrearUsuarioCas03(){
        given(usuarioRepository.findByUsername(usuario.getUsername())).willReturn(Optional.empty());
        given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.empty());
        var usuario1 = usuarioService.createUsuario(usuario);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(4)
    void testObtenerUsuario(){
        given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.of(usuario));
        var usuario1 = usuarioService.getUsuarioById(usuario.getIdUsuario());
        assertThat(usuario1).isNotNull();
    }


    @Test
    @Order(5)
    void testActualizarUsuarioCase1(){
        given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.of(usuario));
        given(rolRepository.findById(rol.getIdRol())).willReturn(Optional.of(rol));
        given(usuarioRepository.save(usuario)).willReturn(usuario);
        var usuario1 = usuarioService.updateUsuario(usuario.getIdUsuario(),usuario);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(6)
    void testActualizarUsuarioCaso2(){

        given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.of(usuario));
        given(usuarioRepository.findByUsername(usuarioCaso2.getUsername())).willReturn(Optional.of(usuarioCaso2));
        var usuario1 = usuarioService.updateUsuario(usuario.getIdUsuario(),usuarioCaso2);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(7)
    void testActualizarUsuarioCaso3(){
        given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.empty());
        var usuario1 = usuarioService.updateUsuario(usuario.getIdUsuario(),usuarioCaso2);
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(8)
    void testEliminarUsuarioCaso1(){
        given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.of(usuario));
        var usuario1 = usuarioService.deleteUsuario(usuario.getIdUsuario());
        assertThat(usuario1).isNotNull();
    }

    @Test
    @Order(9)
    void testEliminarUsuarioCaso2(){
        given(usuarioRepository.findById(usuario.getIdUsuario())).willReturn(Optional.empty());
        var usuario1 = usuarioService.deleteUsuario(usuario.getIdUsuario());
        assertThat(usuario1).isNotNull();
    }

}
