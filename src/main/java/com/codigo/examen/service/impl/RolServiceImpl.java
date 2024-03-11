package com.codigo.examen.service.impl;

import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {
    private final RolRepository rolRepository;


    @Override
    public ResponseEntity<Rol> createRol(Rol rol) {
        Optional<Rol> existingRol = rolRepository.findByNombreRol(rol.getNombreRol());
        if (existingRol.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
         var rol1 = rolRepository.save(rol);
         return ResponseEntity.ok(rol1);
    }

    @Override
    public ResponseEntity<Rol> getRolById(Long id) {

        Optional<Rol> rol = rolRepository.findById(id);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Rol> deleteRol(Long id) {
        Optional<Rol> rol = rolRepository.findById(id);
        if (rol.isPresent()) {
            rolRepository.delete(rol.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
