import { BaseEntity } from './../../shared';

export class Aluno implements BaseEntity {
    constructor(
        public id?: number,
        public matricula?: number,
        public pessoa?: BaseEntity,
    ) {
    }
}
