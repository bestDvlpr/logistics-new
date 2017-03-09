export class Address {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public latitude?: string,
        public longitude?: string,
        public countryId?: number,
        public regionId?: number,
        public cityId?: number,
        public districtId?: number,
        public clientId?: number,
        public receiptsId?: number ) {
    }
}
