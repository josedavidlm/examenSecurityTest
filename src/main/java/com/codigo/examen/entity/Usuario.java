package com.codigo.examen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean accountnonexpire = false;

    @Column(nullable = false)
    private boolean accountnonlocked = false;

    @Column(nullable = false)
    private boolean credentialsnonexpired = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> roles = new HashSet<>();
    public Set<String> getRolesNames() {
        return roles.stream().map(Rol::getNombreRol).collect(Collectors.toSet());
    }

    public Set<SimpleGrantedAuthority> getAutorities() {
        return roles.stream()
                .map(Rol::getNombreRol)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getAutorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountnonexpire;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountnonlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsnonexpired;
    }
}