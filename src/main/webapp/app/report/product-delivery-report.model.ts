/**
 * @author: hasan @date: 6/3/17.
 */
export class ProductDeliveryReport {
    constructor(public companyName?: string,
                public date?: string,
                public id?: number,
                public productName?: string,
                public productQty?: number,
                public docDate?: string,
                public clientFirstName?: string,
                public clientLastName?: string,
                public phoneNumber?: string,
                public districtName?: string,
                public address?: string,
                public carModel?: string,
                public carNumber?: string,
                public driverFirstName?: string,
                public driverLastName?: string,
                public sentToDCTime?: string,
                public deliveredTime?: string,
                public deliveryTookTime?: string,
                public companyId?: number, ) {
    }
}
