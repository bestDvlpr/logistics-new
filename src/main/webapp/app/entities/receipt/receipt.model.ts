import {ProductEntry} from '../product-entry/product-entry.model';
import {Address} from '../address/address.model';
import {Car} from '../car/car.model';
import {Client} from '../client/client.model';
export enum DocType {
    RETURN = <any>'RETURN',
    SALES = <any>'SALES'
}
export enum WholeSaleFlag {
    RETAIL = <any>'RETAIL',
    WHOLESALE = <any>'WHOLESALE'
}
export enum ReceiptStatus {
    NEW = <any>'NEW',
    APPLICATION_SENT = <any>'APPLICATION_SENT',
    ATTACHED_TO_DRIVER = <any>'ATTACHED_TO_DRIVER',
    DELIVERY_PROCESS = <any>'DELIVERY_PROCESS',
    DELIVERED = <any>'DELIVERED',
    CANCELLED = <any>'CANCELLED'
}
export class Receipt {
    public productEntries: ProductEntry[];
    public addresses: Address[];
    public cars: Car[];
    public client: Client;

    constructor(public id?: number,
                public docNum?: string,
                public docID?: string,
                public docType?: DocType,
                public previousDocID?: string,
                public docDate?: number,
                public wholeSaleFlag?: WholeSaleFlag,
                public status?: ReceiptStatus,
                public payMasterId?: number,
                public loyaltyCardId?: number,
                public clientId?: number ) {
    }
}
