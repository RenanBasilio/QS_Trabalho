import { BaseEntity } from './../../shared';

export class Professor implements BaseEntity {
    constructor(
        public id?: number,
        public siape?: number,
        public pessoa?: BaseEntity,
    ) {
    }
}
