import { BaseEntity, User } from './../../shared';

export class Pessoa implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public email?: string,
        public user?: User,
    ) {
    }
}
