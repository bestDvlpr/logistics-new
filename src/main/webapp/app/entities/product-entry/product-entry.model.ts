import {Address} from '../address/address.model';
import {Product} from '../product/product.model';
export enum SalesType {
    TAKEOUT_IN_TIME = <any>'TAKEOUT_IN_TIME',
    TAKEOUT = <any>'TAKEOUT',
    DELIVERY = <any>'DELIVERY'
}
export enum SalesPlace {
    STORE = <any>'STORE',
    WAREHOUSE = <any>'WAREHOUSE'
}
export enum DefectFlag {
    WELL = <any>'WELL',
    DEFECTED = <any>'DEFECTED'
}
export enum VirtualFlag {
    SOLD_PHYSICALLY = <any>'SOLD_PHYSICALLY',
    SOLD_VIRTUALLY = <any>'SOLD_VIRTUALLY'
}
export enum ReceiptStatus {
    APPLICATION_SENT = <any>'APPLICATION_SENT',
    NEW = <any>'NEW',
    ATTACHED_TO_DRIVER = <any>'ATTACHED_TO_DRIVER',
    DELIVERY_PROCESS = <any>'DELIVERY_PROCESS',
    DELIVERED = <any>'DELIVERED'
}
export class ProductEntry {
    public address: Address;
    public product: Product;
    public selected = false;

    constructor(public id?: number,
                public price?: number,
                public deliveryFlag?: SalesType,
                public hallFlag?: SalesPlace,
                public defectFlag?: DefectFlag,
                public virtualFlag?: VirtualFlag,
                public reason?: string,
                public comment?: string,
                public guid?: string,
                public qty?: number,
                public discount?: number,
                public status?: ReceiptStatus,
                public cancelled?: boolean,
                public serialNumber?: string,
                public productId?: number,
                public sellerIDId?: number,
                public receiptId?: number,
                public receiptDocId?: string,
                public receiptDocNum?: string,
                public driverId?: number,
                public attachedCarId?: number,
                public attachedCarNumber?: string,
                public attachedToCarTime?: any,
                public deliveryStartTime?: any,
                public deliveryEndTime?: any,
                public attachedToDriverById?: number,
                public deliveryItemsSentById?: number,
                public markedAsDeliveredId?: number,
                public addressId?: number,
                public companyId?: number,
                public companyName?: string,
                public deliveryDate?: number, ) {
    }
}
