package com.g1.disciplina.repository;

import com.g1.disciplina.domain.Programa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Programa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {

}
