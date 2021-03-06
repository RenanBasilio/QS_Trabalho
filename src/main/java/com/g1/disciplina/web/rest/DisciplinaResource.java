package com.g1.disciplina.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.g1.disciplina.domain.Disciplina;

import com.g1.disciplina.repository.DisciplinaRepository;
import com.g1.disciplina.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;


//para recuperar disciplinas do professor
import com.g1.disciplina.security.SecurityUtils;
import com.g1.disciplina.security.AuthoritiesConstants;
import java.util.ArrayList;
import java.util.Set;
import com.g1.disciplina.repository.UserRepository;

/**
 * REST controller for managing Disciplina.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "disciplina";

    private final DisciplinaRepository disciplinaRepository;
    public DisciplinaResource(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    /**
     * POST  /disciplinas : Create a new disciplina.
     *
     * @param disciplina the disciplina to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disciplina, or with status 400 (Bad Request) if the disciplina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disciplinas")
    @Timed
    public ResponseEntity<Disciplina> createDisciplina(@Valid @RequestBody Disciplina disciplina) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplina);
        if (disciplina.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new disciplina cannot already have an ID")).body(null);
        }
        Disciplina result = disciplinaRepository.save(disciplina);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disciplinas : Updates an existing disciplina.
     *
     * @param disciplina the disciplina to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disciplina,
     * or with status 400 (Bad Request) if the disciplina is not valid,
     * or with status 500 (Internal Server Error) if the disciplina couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disciplinas")
    @Timed
    public ResponseEntity<Disciplina> updateDisciplina(@Valid @RequestBody Disciplina disciplina) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplina);
        if (disciplina.getId() == null) {
            return createDisciplina(disciplina);
        }
        Disciplina result = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disciplina.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disciplinas : get all the disciplinas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of disciplinas in body
     */
    @GetMapping("/disciplinas")
    @Timed
    public List<Disciplina> getAllDisciplinas() {
        log.debug("REST request to get all Disciplinas");

        if ( SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PROFESSOR) )
        {
          String currentUserLogin = SecurityUtils.getCurrentUserLogin();
          List<Disciplina> retrievedList = disciplinaRepository.findAllWithEagerRelationships();
          ArrayList returnList = new ArrayList<Disciplina>();

          for (Disciplina disciplina : retrievedList)
          {

              String disciplinaLogin = "";

              try
              {
                disciplinaLogin = disciplina.getProfessor().getPessoa().getUser().getLogin();
              }

              catch(NullPointerException e)
              {
                System.out.println(e);
              }

              if ( disciplinaLogin.equals(currentUserLogin) )
              {
                returnList.add(disciplina);
              }

          }

          return returnList;

        }


        else return disciplinaRepository.findAllWithEagerRelationships();

        }

    /**
     * GET  /disciplinas/:id : get the "id" disciplina.
     *
     * @param id the id of the disciplina to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disciplina, or with status 404 (Not Found)
     */
    @GetMapping("/disciplinas/{id}")
    @Timed
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        Disciplina disciplina = disciplinaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(disciplina));
    }

    /**
     * DELETE  /disciplinas/:id : delete the "id" disciplina.
     *
     * @param id the id of the disciplina to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disciplinas/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
