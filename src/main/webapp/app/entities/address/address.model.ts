export class Address {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public countryId?: number,
        public regionId?: number,
        public cityId?: number,
        public districtId?: number,
    ) { }
}
