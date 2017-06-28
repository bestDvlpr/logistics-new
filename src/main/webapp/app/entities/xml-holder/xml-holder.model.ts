export class XmlHolder {
    constructor(public id?: number,
                public xmlContent?: string,
                public date?: any,
                public checked?: boolean) {
        this.checked = false;
    }
}
