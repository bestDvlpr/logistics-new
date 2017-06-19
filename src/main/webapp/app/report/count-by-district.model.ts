/**
 * @author: hasan @date: 6/17/17.
 */
export class CountByDistrictReport {

    constructor(public districtName?: string,
                public districtName2?: string,
                public countByCompanies?: ByDistrict[]) {
    }
}

/**
 * @author: hasan @date: 6/3/17.
 */
export class ByDistrict {

    constructor(public companyId?: string,
                public companyName?: string,
                public districtName?: string,
                public districtName2?: string,
                public count?: number) {
    }
}
