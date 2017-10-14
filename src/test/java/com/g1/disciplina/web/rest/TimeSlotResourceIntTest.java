package com.g1.disciplina.web.rest;

import com.g1.disciplina.QsTrabalhoDisciplinaApp;

import com.g1.disciplina.domain.TimeSlot;
import com.g1.disciplina.repository.TimeSlotRepository;
import com.g1.disciplina.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.g1.disciplina.domain.enumeration.DiaSemana;
/**
 * Test class for the TimeSlotResource REST controller.
 *
 * @see TimeSlotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsTrabalhoDisciplinaApp.class)
public class TimeSlotResourceIntTest {

    private static final DiaSemana DEFAULT_DIA = DiaSemana.DOMINGO;
    private static final DiaSemana UPDATED_DIA = DiaSemana.SEGUNDA;

    private static final Long DEFAULT_HORA_INICIO = 0L;
    private static final Long UPDATED_HORA_INICIO = 1L;

    private static final Long DEFAULT_HORA_FIM = 0L;
    private static final Long UPDATED_HORA_FIM = 1L;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeSlotMockMvc;

    private TimeSlot timeSlot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeSlotResource timeSlotResource = new TimeSlotResource(timeSlotRepository);
        this.restTimeSlotMockMvc = MockMvcBuilders.standaloneSetup(timeSlotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSlot createEntity(EntityManager em) {
        TimeSlot timeSlot = new TimeSlot()
            .dia(DEFAULT_DIA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM);
        return timeSlot;
    }

    @Before
    public void initTest() {
        timeSlot = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeSlot() throws Exception {
        int databaseSizeBeforeCreate = timeSlotRepository.findAll().size();

        // Create the TimeSlot
        restTimeSlotMockMvc.perform(post("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
            .andExpect(status().isCreated());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSlot testTimeSlot = timeSlotList.get(timeSlotList.size() - 1);
        assertThat(testTimeSlot.getDia()).isEqualTo(DEFAULT_DIA);
        assertThat(testTimeSlot.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testTimeSlot.getHoraFim()).isEqualTo(DEFAULT_HORA_FIM);
    }

    @Test
    @Transactional
    public void createTimeSlotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeSlotRepository.findAll().size();

        // Create the TimeSlot with an existing ID
        timeSlot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSlotMockMvc.perform(post("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setDia(null);

        // Create the TimeSlot, which fails.

        restTimeSlotMockMvc.perform(post("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setHoraInicio(null);

        // Create the TimeSlot, which fails.

        restTimeSlotMockMvc.perform(post("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFimIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeSlotRepository.findAll().size();
        // set the field null
        timeSlot.setHoraFim(null);

        // Create the TimeSlot, which fails.

        restTimeSlotMockMvc.perform(post("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
            .andExpect(status().isBadRequest());

        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeSlots() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        // Get all the timeSlotList
        restTimeSlotMockMvc.perform(get("/api/time-slots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSlot.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.intValue())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.intValue())));
    }

    @Test
    @Transactional
    public void getTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        // Get the timeSlot
        restTimeSlotMockMvc.perform(get("/api/time-slots/{id}", timeSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeSlot.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.intValue()))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeSlot() throws Exception {
        // Get the timeSlot
        restTimeSlotMockMvc.perform(get("/api/time-slots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();

        // Update the timeSlot
        TimeSlot updatedTimeSlot = timeSlotRepository.findOne(timeSlot.getId());
        updatedTimeSlot
            .dia(UPDATED_DIA)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM);

        restTimeSlotMockMvc.perform(put("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeSlot)))
            .andExpect(status().isOk());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate);
        TimeSlot testTimeSlot = timeSlotList.get(timeSlotList.size() - 1);
        assertThat(testTimeSlot.getDia()).isEqualTo(UPDATED_DIA);
        assertThat(testTimeSlot.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testTimeSlot.getHoraFim()).isEqualTo(UPDATED_HORA_FIM);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeSlot() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();

        // Create the TimeSlot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimeSlotMockMvc.perform(put("/api/time-slots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
            .andExpect(status().isCreated());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);
        int databaseSizeBeforeDelete = timeSlotRepository.findAll().size();

        // Get the timeSlot
        restTimeSlotMockMvc.perform(delete("/api/time-slots/{id}", timeSlot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeSlot> timeSlotList = timeSlotRepository.findAll();
        assertThat(timeSlotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeSlot.class);
        TimeSlot timeSlot1 = new TimeSlot();
        timeSlot1.setId(1L);
        TimeSlot timeSlot2 = new TimeSlot();
        timeSlot2.setId(timeSlot1.getId());
        assertThat(timeSlot1).isEqualTo(timeSlot2);
        timeSlot2.setId(2L);
        assertThat(timeSlot1).isNotEqualTo(timeSlot2);
        timeSlot1.setId(null);
        assertThat(timeSlot1).isNotEqualTo(timeSlot2);
    }
}
