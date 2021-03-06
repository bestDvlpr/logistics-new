import {Receipt} from './receipt.model';
import {Client} from '../client/client.model';
import {Address} from '../address/address.model';
import {ProductEntry} from '../product-entry/product-entry.model';
import {ACElement} from '../../shared/autocomplete/element.model';
import {Company} from '../company/company.model';
import {isNullOrUndefined} from 'util';
import {CountByDistrictReport} from '../../report/count-by-district.model';
import {Injectable, OnInit} from '@angular/core';
import {JhiLanguageHelper} from '../../shared/language/language.helper';
import {TranslateService} from '@ngx-translate/core';
/**
 * @author: hasan @date: 3/11/17.
 */
@Injectable()
export class DataHolderService implements OnInit {
    _receipt: Receipt = null;
    _client: Client = null;
    _address: Address = null;
    _selectedProducts: ProductEntry[] = null;
    _leftProducts: ProductEntry[] = null;
    _autocompleteObjects: ACElement[] = null;
    productCarExists = false;
    _autocompleteSelected: ACElement = null;
    _company: Company = null;
    languages: any[];

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
        const month = date.getMonth() + 1;
        const fullYear = date.getFullYear();
        const month2 = ((month < 10) ? '0' + month : month);
        const date2 = ((date.getDate() < 10) ? '0' + date.getDate() : date.getDate());
        const s = fullYear + '-' + month2 + '-' + date2;
        return {s};
    }

    constructor(private translateService: TranslateService,
                private languageHelper: JhiLanguageHelper) {
    }

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
            for (const entry of this._receipt.productEntries) {
                if (entry.attachedCarId != null) {
                    this.productCarExists = true;
                }
            }
        }
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    drawChart(report: CountByDistrictReport) {
        const data: any = [];
        for (const a of report.countByCompanies) {
            data.push({
                name: (a.companyName !== null ? a.companyName : this.translateService.instant('logisticsApp.report.other')) + ': ' + a.count,
                y: a.count
            });
        }

        let allCount = 0;
        report.countByCompanies.forEach((value) => allCount += value.count);

        let district = null;
        if (report.districtName !== null) {
            district = report.districtName;
        } else if (report.districtName2) {
            district = report.districtName2;
        } else {
            district = this.translateService.instant('logisticsApp.report.other');
        }

        return {
            title: {
                text: district + ': ' + allCount
            },
            series: [{
                name: report.districtName !== null ? report.districtName : this.translateService.instant('logisticsApp.report.other'),
                data
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
            },
            navigation: {
                buttonOptions: {
                    enabled: true
                }
            },
            yAxis: {
                title: {
                    text: this.translateService.instant('logisticsApp.report.count')
                }
            },
            exporting: {
                allowHTML: true,
                chartOptions: { // specific options for the exported image
                    plotOptions: {
                        series: {
                            dataLabels: {
                                enabled: true
                            }
                        }
                    }
                },
                fallbackToExportServer: false
            }
        };
    }

    drawColumnChart(report: CountByDistrictReport) {
        const data: any = [];
        const companyNames = [];
        for (const a of report.countByCompanies) {
            data.push({
                y: a.count
            });

            if (companyNames.indexOf(a.companyName) === -1) {
                companyNames.push(a.companyName);
            }
        }

        let allCount = 0;
        report.countByCompanies.forEach((value) => allCount += value.count);

        let district = null;
        if (report.districtName !== null) {
            district = report.districtName;
        } else if (report.districtName2) {
            district = report.districtName2;
        } else {
            district = this.translateService.instant('logisticsApp.report.other');
        }

        return {
            title: {
                text: district + ': ' + allCount
            },
            series: [{
                name: report.districtName !== null ? report.districtName : this.translateService.instant('logisticsApp.report.other'),
                data,
                colorByPoint: true
            }],
            chart: {
                type: 'column',
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
            },
            navigation: {
                buttonOptions: {
                    enabled: true
                }
            },
            yAxis: {
                title: {
                    text: this.translateService.instant('logisticsApp.report.count')
                }
            },
            exporting: {
                allowHTML: true,
                chartOptions: { // specific options for the exported image
                    plotOptions: {
                        series: {
                            dataLabels: {
                                enabled: true
                            }
                        }
                    }
                },
                fallbackToExportServer: false
            },
            xAxis: {
                categories: companyNames,
                crosshair: true
            }
        };
    }
}
