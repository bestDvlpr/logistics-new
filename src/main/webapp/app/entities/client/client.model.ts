import {Address} from '../address/address.model';
import {PhoneNumber} from '../phone-number/phone-number.model';
export class Client {
    public phoneNumbers: string[] = [];
    public numbers: PhoneNumber[] = [];

    constructor(public id?: number,
                public firstName?: string,
                public lastName?: string,
                public regDate?: any,
                public bankName?: string,
                public bankFilialRegion?: string,
                public bankAccountNumber?: string,
                public mfo?: string,
                public tin?: string,
                public okonx?: string,
                public oked?: string,
                public addressDTOS?: Address[]) {
    }
}
