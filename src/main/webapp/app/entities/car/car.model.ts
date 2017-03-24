export enum CarStatus {
    IDLE = <any>'IDLE',
    BUSY = <any>'BUSY'

}
export class Car {
    constructor(public id?: number,
                public number?: string,
                public deleted?: boolean,
                public status?: CarStatus,
                public carModelId?: number,
                public carColorId?: number,
                public typeId?: number,) {
        this.deleted = false;
    }
}
