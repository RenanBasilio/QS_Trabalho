import { BaseEntity } from './../../shared';

const enum EstadoDisciplina {
    'INSCRICAO',
    'TRANCAMENTO',
    'CORRENTE',
    'FINALIZADA'
}

const enum TipoDisciplina {
    'CURSO',
    'PESQUISA'
}

export class Disciplina implements BaseEntity {
    constructor(
        public id?: number,
        public codigo?: string,
        public nome?: string,
        public estado?: EstadoDisciplina,
        public trimestre?: number,
        public cargaHoraria?: number,
        public creditos?: number,
        public tipo?: TipoDisciplina,
        public professor?: BaseEntity,
        public programa?: BaseEntity,
        public requisitos?: BaseEntity[],
    ) {
    }
}
