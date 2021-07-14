package org.sid.springmvc.dao;

import org.sid.springmvc.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByUsername(String username);
    public Utilisateur save(Utilisateur user);
    public void delete(Utilisateur user);
    public Optional<Utilisateur> findById(Long id);
}
