import { LocationType } from '../location-type';
export class Location {
    constructor(
        public id?: number,
        public name?: string,
        public parent?: Location,
        public type?: LocationType,
    ) { }
}
