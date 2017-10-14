package com.g1.disciplina.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.g1.disciplina.domain.Programa;

import com.g1.disciplina.repository.ProgramaRepository;
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

/**
 * REST controller for managing Programa.
 */
@RestController
@RequestMapping("/api")
public class ProgramaResource {

    private final Logger log = LoggerFactory.getLogger(ProgramaResource.class);

    private static final String ENTITY_NAME = "programa";

    private final ProgramaRepository programaRepository;
    public ProgramaResource(ProgramaRepository programaRepository) {
        this.programaRepository = programaRepository;
    }

    /**
     * POST  /programas : Create a new programa.
     *
     * @param programa the programa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programa, or with status 400 (Bad Request) if the programa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/programas")
    @Timed
    public ResponseEntity<Programa> createPrograma(@RequestBody Programa programa) throws URISyntaxException {
        log.debug("REST request to save Programa : {}", programa);
        if (programa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new programa cannot already have an ID")).body(null);
        }
        Programa result = programaRepository.save(programa);
        return ResponseEntity.created(new URI("/api/programas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /programas : Updates an existing programa.
     *
     * @param programa the programa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programa,
     * or with status 400 (Bad Request) if the programa is not valid,
     * or with status 500 (Internal Server Error) if the programa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/programas")
    @Timed
    public ResponseEntity<Programa> updatePrograma(@RequestBody Programa programa) throws URISyntaxException {
        log.debug("REST request to update Programa : {}", programa);
        if (programa.getId() == null) {
            return createPrograma(programa);
        }
        Programa result = programaRepository.save(programa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, programa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /programas : get all the programas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of programas in body
     */
    @GetMapping("/programas")
    @Timed
    public List<Programa> getAllProgramas() {
        log.debug("REST request to get all Programas");
        return programaRepository.findAll();
        }

    /**
     * GET  /programas/:id : get the "id" programa.
     *
     * @param id the id of the programa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programa, or with status 404 (Not Found)
     */
    @GetMapping("/programas/{id}")
    @Timed
    public ResponseEntity<Programa> getPrograma(@PathVariable Long id) {
        log.debug("REST request to get Programa : {}", id);
        Programa programa = programaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(programa));
    }

    /**
     * DELETE  /programas/:id : delete the "id" programa.
     *
     * @param id the id of the programa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/programas/{id}")
    @Timed
    public ResponseEntity<Void> deletePrograma(@PathVariable Long id) {
        log.debug("REST request to delete Programa : {}", id);
        programaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
