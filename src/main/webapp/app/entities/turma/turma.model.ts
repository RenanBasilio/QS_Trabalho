import { BaseEntity } from './../../shared';

export class Turma implements BaseEntity {
    constructor(
        public id?: number,
        public disciplina?: BaseEntity,
        public sala?: BaseEntity,
        public horarios?: BaseEntity[],
    ) {
    }
}
