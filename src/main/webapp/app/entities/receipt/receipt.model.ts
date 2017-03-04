export enum DocType {
    'RETURN',
    'SALES'
}
export enum WholeSaleFlag {
    'RETAIL',
    'WHOLESALE'
}
export enum ReceiptStatus {
    'NEW',
    'APPLICATION_SENT',
    'ATTACHED_TO_DRIVER',
    'DELIVERY_PROCESS',
    'DELIVERED'
}
export class Receipt {
    constructor(
        public id?: number,
        public docNum?: string,
        public docID?: string,
        public docType?: DocType,
        public previousDocID?: string,
        public docDate?: number,
        public wholeSaleFlag?: WholeSaleFlag,
        public status?: ReceiptStatus,
        public payMasterId?: number,
        public loyaltyCardId?: number,
        public clientId?: number,
        public productEntriesId?: number,
    ) { }
}
