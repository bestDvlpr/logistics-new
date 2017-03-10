import {Address} from '../address/address.model';
export class Client {
    constructor(public id?: number,
                public firstName?: string,
                public lastName?: string,
                public regDate?: any,
                public addressDTOS?: Address[]) {
    }
}
