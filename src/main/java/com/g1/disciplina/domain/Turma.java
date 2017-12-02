package com.g1.disciplina.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Turma.
 */
@Entity
@Table(name = "turma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Disciplina disciplina;

    @OneToOne
    @JoinColumn(unique = true)
    private Local sala;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "turma_horario",
               joinColumns = @JoinColumn(name="turmas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="horarios_id", referencedColumnName="id"))
    private Set<TimeSlot> horarios = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Turma disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Local getSala() {
        return sala;
    }

    public Turma sala(Local local) {
        this.sala = local;
        return this;
    }

    public void setSala(Local local) {
        this.sala = local;
    }

    public Set<TimeSlot> getHorarios() {
        return horarios;
    }

    public Turma horarios(Set<TimeSlot> timeSlots) {
        this.horarios = timeSlots;
        return this;
    }

    public Turma addHorario(TimeSlot timeSlot) {
        this.horarios.add(timeSlot);
        return this;
    }

    public Turma removeHorario(TimeSlot timeSlot) {
        this.horarios.remove(timeSlot);
        return this;
    }

    public void setHorarios(Set<TimeSlot> timeSlots) {
        this.horarios = timeSlots;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Turma turma = (Turma) o;
        if (turma.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), turma.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Turma{" +
            "id=" + getId() +
            "}";
    }
}
