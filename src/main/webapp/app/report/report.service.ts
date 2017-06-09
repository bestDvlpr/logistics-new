import {BaseRequestOptions, Http, Response, ResponseContentType} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Injectable} from "@angular/core";
import {CommonReportCriteria, ReportCriteria} from "./report.criteria";
/**
 * @author: hasan @date: 6/3/17.
 */
@Injectable()
export class ReportService {
    private resourceUrl = 'api/report';

    constructor(private http: Http) {
    }

    getGenericReport(params: CommonReportCriteria): Observable<Response> {
        let copy: CommonReportCriteria = Object.assign({}, params);
        return this.http.post(this.resourceUrl.concat('/generic'), copy);
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: ReportCriteria = new CommonReportCriteria('2017-04-29', '2017-05-29', {
                id: 1,
                name: 'name'
            }, {id: 2, name: 'name'});
            // params.set('page', req.page);
            // params.set('size', req.size);
            // if (req.sort) {
            //     params.paramsMap.set('sort', req.sort);
            // }
            // params.set('query', req.query);

            options.body = params;
        }
        return options;
    }

    downloadGenericReport(criteria: CommonReportCriteria) {
        let mediaType = ResponseContentType.Blob;
        return this.http.post(this.resourceUrl.concat('/generic/export'), criteria, {responseType: mediaType});
    }
}
