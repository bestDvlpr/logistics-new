const enum PaymentType {
    'CASH',
    'CARD',
    'LOYALTY_CARD'
};
export class PayType {
    constructor(
        public id?: number,
        public amount?: number,
        public sapCode?: string,
        public serial?: string,
        public paymentType?: PaymentType,
        public receiptId?: number,
    ) { }
}
