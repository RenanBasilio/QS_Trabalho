import { BaseEntity } from './../../shared';

export class Programa implements BaseEntity {
    constructor(
        public id?: number,
        public sigla?: string,
        public nome?: string,
    ) {
    }
}
