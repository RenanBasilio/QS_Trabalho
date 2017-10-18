package com.g1.disciplina.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.g1.disciplina.domain.enumeration.DiaSemana;

/**
 * A TimeSlot.
 */
@Entity
@Table(name = "time_slot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false)
    private DiaSemana dia;

    @NotNull
    @Min(value = 0L)
    @Max(value = 2400L)
    @Column(name = "hora_inicio", nullable = false)
    private Long horaInicio;

    @NotNull
    @Min(value = 0L)
    @Max(value = 2400L)
    @Column(name = "hora_fim", nullable = false)
    private Long horaFim;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public TimeSlot dia(DiaSemana dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public Long getHoraInicio() {
        return horaInicio;
    }

    public TimeSlot horaInicio(Long horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(Long horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Long getHoraFim() {
        return horaFim;
    }

    public TimeSlot horaFim(Long horaFim) {
        this.horaFim = horaFim;
        return this;
    }

    public void setHoraFim(Long horaFim) {
        this.horaFim = horaFim;
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
        TimeSlot timeSlot = (TimeSlot) o;
        if (timeSlot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeSlot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            "}";
    }
}
