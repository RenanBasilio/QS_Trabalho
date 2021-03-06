package com.g1.disciplina.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.g1.disciplina.domain.Turma;

import com.g1.disciplina.repository.TurmaRepository;
import com.g1.disciplina.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

//para recuperar turmas do professor
import com.g1.disciplina.security.SecurityUtils;
import com.g1.disciplina.security.AuthoritiesConstants;
import java.util.ArrayList;
import java.util.Set;
import com.g1.disciplina.repository.UserRepository;

/**
 * REST controller for managing Turma.
 */
@RestController
@RequestMapping("/api")
public class TurmaResource {

    private final Logger log = LoggerFactory.getLogger(TurmaResource.class);

    private static final String ENTITY_NAME = "turma";

    private final TurmaRepository turmaRepository;
    public TurmaResource(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    /**
     * POST  /turmas : Create a new turma.
     *
     * @param turma the turma to create
     * @return the ResponseEntity with status 201 (Created) and with body the new turma, or with status 400 (Bad Request) if the turma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/turmas")
    @Timed
    public ResponseEntity<Turma> createTurma(@RequestBody Turma turma) throws URISyntaxException {
        log.debug("REST request to save Turma : {}", turma);
        if (turma.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new turma cannot already have an ID")).body(null);
        }
        Turma result = turmaRepository.save(turma);
        return ResponseEntity.created(new URI("/api/turmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /turmas : Updates an existing turma.
     *
     * @param turma the turma to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated turma,
     * or with status 400 (Bad Request) if the turma is not valid,
     * or with status 500 (Internal Server Error) if the turma couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/turmas")
    @Timed
    public ResponseEntity<Turma> updateTurma(@RequestBody Turma turma) throws URISyntaxException {
        log.debug("REST request to update Turma : {}", turma);
        if (turma.getId() == null) {
            return createTurma(turma);
        }
        Turma result = turmaRepository.save(turma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, turma.getId().toString()))
            .body(result);
    }

    /**
     * GET  /turmas : get all the turmas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of turmas in body
     */
    @GetMapping("/turmas")
    @Timed
    public List<Turma> getAllTurmas() {
        log.debug("REST request to get all Turmas");

        if ( SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PROFESSOR) )
        {
          String currentUserLogin = SecurityUtils.getCurrentUserLogin();
          List<Turma> retrievedList = turmaRepository.findAllWithEagerRelationships();
          ArrayList returnList = new ArrayList<Turma>();

          for (Turma turma : retrievedList)
          {

              String turmaLogin = "";

              try
              {
                turmaLogin = turma.getDisciplina().getProfessor().getPessoa().getUser().getLogin();
              }

              catch(NullPointerException e)
              {
                System.out.println(e);
              }

              if ( turmaLogin.equals(currentUserLogin) )
              {
                returnList.add(turma);
              }

          }

          return returnList;

        }

        else return turmaRepository.findAllWithEagerRelationships();
    }


    /**
     * GET  /turmas/:id : get the "id" turma.
     *
     * @param id the id of the turma to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the turma, or with status 404 (Not Found)
     */
    @GetMapping("/turmas/{id}")
    @Timed
    public ResponseEntity<Turma> getTurma(@PathVariable Long id) {
        log.debug("REST request to get Turma : {}", id);
        Turma turma = turmaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(turma));
    }

    /**
     * DELETE  /turmas/:id : delete the "id" turma.
     *
     * @param id the id of the turma to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/turmas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        log.debug("REST request to delete Turma : {}", id);
        turmaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
