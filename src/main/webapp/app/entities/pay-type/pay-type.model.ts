export enum PaymentType {
    CASH = <any>'CASH',
    CARD = <any>'CARD',
    ZSRT = <any>'GIFT CERTIFICATE',
    ZCUP = <any>'COUPON',
    ZSRTC = <any>'CERTIFICATE WITH CHANGE',
    ZBAL = <any>'PAYMENT WITH SCORE',
    ZCUPS = <any>'COUPON WITH CHANGE',
    LOYALTY_CARD = <any>'LOYALTY_CARD'
}
export class PayType {
    constructor(public id?: number,
                public sapCode?: string,
                public serial?: string,
                public paymentType?: PaymentType,
                public amount?: number,
                public receiptId?: number) {
    }
}
