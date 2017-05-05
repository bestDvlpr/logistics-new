export enum CompanyType {
    WAREHOUSE = <any>'WAREHOUSE',
    SHOP = <any>'SHOP',
    DELIVERY_CENTER = <any>'DELIVERY_CENTER',
    DELIVERY_CENTER_BRANCH = <any>'DELIVERY_CENTER_BRANCH'

}
export class Company {
    constructor(public id?: number,
                public name?: string,
                public idNumber?: string,
                public type?: CompanyType,
                public addressId?: number,
                public addressStreetAddress?: string, ) {
    }
}
