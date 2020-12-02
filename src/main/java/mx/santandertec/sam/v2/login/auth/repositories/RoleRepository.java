package mx.santandertec.sam.v2.login.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.santandertec.sam.v2.login.auth.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}