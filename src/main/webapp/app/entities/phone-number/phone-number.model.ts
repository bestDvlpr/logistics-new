export enum PhoneType {
    MOBILE = <any>'MOBILE',
    HOME = <any>'HOME',
    WORK = <any>'WORK'
}
import {Client} from '../client';
export class PhoneNumber {
    constructor(public id?: number,
                public number?: string,
                public type?: PhoneType,
                public client?: Client ) {
    }
}
