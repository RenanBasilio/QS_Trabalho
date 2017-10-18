import { BaseEntity } from './../../shared';

export class Local implements BaseEntity {
    constructor(
        public id?: number,
        public centro?: string,
        public bloco?: string,
        public sala?: string,
        public externo?: boolean,
    ) {
        this.externo = false;
    }
}
