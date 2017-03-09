import { Client } from '../client';
export class PhoneNumber {
    constructor(
        public id?: number,
        public number?: string,
        public client?: Client ) { }
}
