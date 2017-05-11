import {ProductEntry} from "../product-entry/product-entry.model";
import {Address} from "../address/address.model";
import {Car} from "../car/car.model";
import {Client} from "../client/client.model";
import {Company} from "../company/company.model";
export enum DocType {
    RETURN = <any>'RETURN',
    SALES = <any>'SALES',
    DISPLACEMENT = <any>'DISPLACEMENT',
    INCOMING = <any>'INCOMING',
    OUTGOING = <any>'OUTGOING',
    CREDIT = <any>'CREDIT',
    INSTALLMENT = <any>'INSTALLMENT'
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
    CANCELLED = <any>'CANCELLED',
    TAKEOUT = <any>'TAKEOUT'
}
export class Receipt {
    public productEntries: ProductEntry[];
    public addresses: Address[] = [];
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
                public sentToDCTime?: any,
                public clientId?: number,
                public payTypesId?: number,
                public sentById?: number,
                public companyId?: number,
                public companyName?: string,
                public fromTime?: string,
                public toTime?: string,
                public markedAsDeliveredById?: number,
                public deliveryDate?: number,
                public address?: Address,
                public deliveredDateTime = '', // formatted datetime as string 'yyyy-MM-dd hh24:mm:ss'
                public receiverId?: number,
                public receiverName?: string,
                public receiver?: Company) {
    }
}
