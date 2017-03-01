export class PayType {
    constructor(
        public id?: number,
        public amount?: number,
        public sapCode?: string,
        public serial?: string,
        public paymentTypeId?: number,
        public receiptId?: number,
    ) { }
}
