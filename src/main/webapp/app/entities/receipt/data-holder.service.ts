import {Receipt} from "./receipt.model";
import {Client} from "../client/client.model";
import {Injectable, OnInit} from "@angular/core";
import {Address} from "../address/address.model";
import {ProductEntry} from "../product-entry/product-entry.model";
import {ACElement} from "../../shared/autocomplete/element.model";
import {Company} from "../company/company.model";
import {isNullOrUndefined} from "util";
import {CountByDistrictReport} from "../../report/count-by-district.model";
/**
 * @author: hasan
 * @date: 3/11/17.
 */
@Injectable()
export class DataHolderService implements OnInit {
    _receipt: Receipt = null;
    _client: Client = null;
    _address: Address = null;
    _selectedProducts: ProductEntry[] = null;
    _leftProducts: ProductEntry[] = null;
    _autocompleteObjects: ACElement[] = null;
    productCarExists: boolean = false;
    _autocompleteSelected: ACElement = null;
    _company: Company = null;

    public clearAll() {
        this._receipt = null;
        this._client = null;
        this._company = null;
        this._address = null;
        this._selectedProducts = [];
        this._leftProducts = [];
        this._autocompleteObjects = [];
        this.productCarExists = false;
        this._autocompleteSelected = null;
    }

    ngOnInit(): void {
        if (this._receipt != null &&
            this._receipt.productEntries != null &&
            this._receipt.productEntries.length > 0) {
            for (let entry of this._receipt.productEntries) {
                if (entry.attachedCarId != null) {
                    this.productCarExists = true;
                }
            }
        }
    }

    static formatYYYYMMDD(date: any) {
        if (isNullOrUndefined(date)) {
            return null;
        }
        return date.year +
            '-' + ((date.month < 10) ? '0' + date.month : date.month) +
            '-' + ((date.day < 10) ? '0' + date.day : date.day);
    }

    static format(date: any) {
        if (isNullOrUndefined(date)) {
            return null;
        }
        let month = date.getMonth() + 1;
        return date.getFullYear() +
            '-' + ((month < 10) ? '0' + month : month) +
            '-' + ((date.getDate() < 10) ? '0' + date.getDate() : date.getDate());
    }

    static drawChart(report: CountByDistrictReport) {
        let data: any = [];
        for (let a of report.countByCompanies) {
            data.push({name: a.companyName + ': ' + a.count, y: a.count});
        }

        let allCount: number = 0;
        report.countByCompanies.forEach(value => allCount += value.count);

        return {
            title: {text: report.districtName + ': ' + allCount},
            series: [{
                name: report.districtName,
                data: data
            }],
            chart: {
                type: 'pie',
                width: null,
                height: null
            },
            credits: {
                enabled: false
            },
            tooltip: {
                headerFormat: '<span style="font-size: 14px; font-weight: bold;">{point.key}</span><br/>',
                style: {
                    fontSize: '15px'
                }
            }
        };
    }
}
