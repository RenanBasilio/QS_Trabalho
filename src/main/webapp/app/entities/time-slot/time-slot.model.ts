import { BaseEntity } from './../../shared';

const enum DiaSemana {
    'DOMINGO',
    'SEGUNDA',
    'TERCA',
    'QUARTA',
    'QUINTA',
    'SEXTA',
    'SABADO'
}

export class TimeSlot implements BaseEntity {
    constructor(
        public id?: number,
        public dia?: DiaSemana,
        public horaInicio?: number,
        public horaFim?: number,
    ) {
    }
}
