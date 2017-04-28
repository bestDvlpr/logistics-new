
const enum CompanyType {
    'WAREHOUSE',
    'SHOP',
    'DELIVERY_CENTER',
    'DELIVERY_CENTER_BRANCH'

};
export class Company {
    constructor(
        public id?: number,
        public name?: string,
        public idNumber?: string,
        public type?: CompanyType,
        public addressId?: number,
    ) {
    }
}
