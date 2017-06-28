import {Company} from '../company/company.model';
export class Address {
    constructor(public id?: number,
                public streetAddress?: string,
                public latitude?: string,
                public longitude?: string,
                public countryId?: number,
                public countryName?: string,
                public regionId?: number,
                public regionName?: string,
                public cityId?: number,
                public cityName?: string,
                public districtId?: number,
                public districtName?: string,
                public clientId?: number,
                public companies?: Company[],
                public companyId?: number,
                public companyName?: string,
                public receiptsId?: number) {
    }
}
