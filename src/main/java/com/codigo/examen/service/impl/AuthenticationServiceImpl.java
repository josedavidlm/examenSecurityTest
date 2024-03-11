package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.UsuarioRepository;
import com.codigo.examen.request.SignInRequest;
import com.codigo.examen.request.SignUpRequest;
import com.codigo.examen.response.AuthenticationResponse;
import com.codigo.examen.service.AuthenticationService;
import com.codigo.examen.service.JWTService;
import com.codigo.examen.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @Override
    public Usuario signUpUser(SignUpRequest signUpRequest) {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        Set<Rol> roles = new HashSet<>();
        rol.setIdRol(2L);
        rol.setNombreRol(Role.USER.name());
        roles.add(rol);
        usuario.setUsername(signUpRequest.getEmail());
        usuario.setPassword(signUpRequest.getPassword());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setRoles(roles);
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return updatedUsuario;
    }

    @Override
    public Usuario signUpAdmin(SignUpRequest signUpRequest) {
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        Set<Rol> roles = new HashSet<>();
        rol.setIdRol(1L);
        rol.setNombreRol(Role.ADMIN.name());
        roles.add(rol);
        usuario.setUsername(signUpRequest.getEmail());
        usuario.setPassword(signUpRequest.getPassword());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setRoles(roles);
        usuario.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return updatedUsuario;
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),signInRequest.getPassword()));
        var user = usuarioRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User name no valido"));

        var jwt = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse =  new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }
}
