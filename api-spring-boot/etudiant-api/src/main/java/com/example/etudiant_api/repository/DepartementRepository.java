package com.example.etudiant_api.repository;

import com.example.etudiant_api.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {}