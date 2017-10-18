package com.g1.disciplina.repository;

import com.g1.disciplina.domain.Inscricao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inscricao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

}
