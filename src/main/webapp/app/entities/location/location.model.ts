export enum LocationType {
    COUNTRY=<any>'COUNTRY',
    REGION = <any>'REGION',
    CITY=<any>'CITY',
    DISTRICT=<any>'DISTRICT'
}
export class Location {
    constructor(
        public id?: number,
        public name?: string,
        public parent?: Location,
        public type?: LocationType,
    ) { }
}
