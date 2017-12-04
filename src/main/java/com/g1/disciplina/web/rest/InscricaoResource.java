package com.g1.disciplina.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.g1.disciplina.domain.Inscricao;

import com.g1.disciplina.repository.InscricaoRepository;
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

//para recuperar inscrições em turmas do professor
import com.g1.disciplina.security.SecurityUtils;
import com.g1.disciplina.security.AuthoritiesConstants;
import java.util.ArrayList;
import java.util.Set;
import com.g1.disciplina.repository.UserRepository;

/**
 * REST controller for managing Inscricao.
 */
@RestController
@RequestMapping("/api")
public class InscricaoResource {

    private final Logger log = LoggerFactory.getLogger(InscricaoResource.class);

    private static final String ENTITY_NAME = "inscricao";

    private final InscricaoRepository inscricaoRepository;
    public InscricaoResource(InscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }

    /**
     * POST  /inscricaos : Create a new inscricao.
     *
     * @param inscricao the inscricao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscricao, or with status 400 (Bad Request) if the inscricao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscricaos")
    @Timed
    public ResponseEntity<Inscricao> createInscricao(@RequestBody Inscricao inscricao) throws URISyntaxException {
        log.debug("REST request to save Inscricao : {}", inscricao);
        if (inscricao.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inscricao cannot already have an ID")).body(null);
        }
        Inscricao result = inscricaoRepository.save(inscricao);
        return ResponseEntity.created(new URI("/api/inscricaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscricaos : Updates an existing inscricao.
     *
     * @param inscricao the inscricao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscricao,
     * or with status 400 (Bad Request) if the inscricao is not valid,
     * or with status 500 (Internal Server Error) if the inscricao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscricaos")
    @Timed
    public ResponseEntity<Inscricao> updateInscricao(@RequestBody Inscricao inscricao) throws URISyntaxException {
        log.debug("REST request to update Inscricao : {}", inscricao);
        if (inscricao.getId() == null) {
            return createInscricao(inscricao);
        }
        Inscricao result = inscricaoRepository.save(inscricao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inscricao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscricaos : get all the inscricaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscricaos in body
     */
    @GetMapping("/inscricaos")
    @Timed
    public List<Inscricao> getAllInscricaos() {
        log.debug("REST request to get all Inscricaos");

        if ( SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.PROFESSOR) )
        {
          String currentUserLogin = SecurityUtils.getCurrentUserLogin();
          List<Inscricao> retrievedList = inscricaoRepository.findAll();
          ArrayList returnList = new ArrayList<Inscricao>();

          for (Inscricao inscricao : retrievedList)
          {

              String inscricaoLogin = "";

              try
              {
                inscricaoLogin = inscricao.getTurma().getDisciplina().getProfessor().getPessoa().getUser().getLogin();
              }

              catch(NullPointerException e)
              {
                System.out.println(e);
              }

              if ( inscricaoLogin.equals(currentUserLogin) )
              {
                returnList.add(inscricao);
              }

          }

          return returnList;

        }

        else return inscricaoRepository.findAll();
        }

    /**
     * GET  /inscricaos/:id : get the "id" inscricao.
     *
     * @param id the id of the inscricao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscricao, or with status 404 (Not Found)
     */
    @GetMapping("/inscricaos/{id}")
    @Timed
    public ResponseEntity<Inscricao> getInscricao(@PathVariable Long id) {
        log.debug("REST request to get Inscricao : {}", id);
        Inscricao inscricao = inscricaoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inscricao));
    }

    /**
     * DELETE  /inscricaos/:id : delete the "id" inscricao.
     *
     * @param id the id of the inscricao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscricaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInscricao(@PathVariable Long id) {
        log.debug("REST request to delete Inscricao : {}", id);
        inscricaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
