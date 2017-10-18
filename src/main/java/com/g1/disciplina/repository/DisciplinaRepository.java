package com.g1.disciplina.repository;

import com.g1.disciplina.domain.Disciplina;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Disciplina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query("select distinct disciplina from Disciplina disciplina left join fetch disciplina.requisitos")
    List<Disciplina> findAllWithEagerRelationships();

    @Query("select disciplina from Disciplina disciplina left join fetch disciplina.requisitos where disciplina.id =:id")
    Disciplina findOneWithEagerRelationships(@Param("id") Long id);

}
