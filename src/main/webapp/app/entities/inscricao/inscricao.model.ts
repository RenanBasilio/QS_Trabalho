import { BaseEntity } from './../../shared';

const enum EstadoInscricao {
    'NORMAL',
    'PENDENTE',
    'TRANCADA',
    'RECUSADA'
}

const enum Nota {
    'A',
    'B',
    'C',
    'D'
}

export class Inscricao implements BaseEntity {
    constructor(
        public id?: number,
        public status?: EstadoInscricao,
        public nota?: Nota,
        public notificado?: boolean,
        public aluno?: BaseEntity,
        public turma?: BaseEntity,
    ) {
        this.notificado = false;
    }
}
