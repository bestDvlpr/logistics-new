import {BaseRequestOptions, Http, Response, ResponseContentType, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {CommonReportCriteria, CountReportCriteria} from './report.criteria';
import {DeliveryCountByCompany} from './delivery-count-by-ompany';
import {ReceiptStatus} from '../entities/receipt/receipt.model';
import {isNullOrUndefined} from 'util';
import {LineChartData} from './line-chart-data.model';
/**
 * @author: hasan @date: 6/3/17.
 */
@Injectable()
export class ReportService {
    private resourceUrl = 'api/report';

    constructor(private http: Http) {
    }

    getGenericReport(params: CommonReportCriteria, req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        const copy: CommonReportCriteria = Object.assign({}, params);
        return this.http.post(this.resourceUrl.concat('/generic'), copy, options);
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    downloadGenericReport(criteria: CommonReportCriteria) {
        const mediaType = ResponseContentType.Blob;
        return this.http.post(this.resourceUrl.concat('/generic/export'), criteria, {responseType: mediaType});
    }

    deliveryCountChart(status: ReceiptStatus, startDate: string, endDate: string): Observable<DeliveryCountByCompany[]> {
        const criteria = new CountReportCriteria(startDate, endDate, null, null, status);
        return this.http.post(this.resourceUrl.concat('/count-by-company-by-status'), criteria).map((res: Response) => {
            return res.json();
        });
    }

    countByCompany(criteria: CommonReportCriteria): Observable<LineChartData[]> {
        if (isNullOrUndefined(criteria)) {
            criteria = Object.assign({}, null);
        }
        return this.http.post(`${this.resourceUrl}/count-by-company`, criteria).map((res: Response) => {
            return res.json();
        });
    }

    countByStatus(criteria: CountReportCriteria): Observable<Response> {
        return this.http.post(this.resourceUrl.concat('/count-by-company-by-status-by-district'), criteria);
    }
}
