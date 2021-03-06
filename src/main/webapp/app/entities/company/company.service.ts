import {Injectable} from '@angular/core';
import {BaseRequestOptions, Http, Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Company, CompanyType} from './company.model';
@Injectable()
export class CompanyService {

    private resourceUrl = 'api/companies';

    constructor(private http: Http) {
    }

    create(company: Company): Observable<Company> {
        const copy: Company = Object.assign({}, company);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(company: Company): Observable<Company> {
        const copy: Company = Object.assign({}, company);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Company> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            ;
    }

    all(): Observable<Response> {
        return this.http.get(this.resourceUrl.concat('/all'));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
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

    byIdNumber(companyId: string) {
        return this.http.get(`${this.resourceUrl}/by-id-number/${companyId}`).map((res: Response) => {
            return res.json();
        });
    }

    autocomplete(companyName: string): Observable<Response> {
        return this.http.get(`${this.resourceUrl.concat('/autocomplete')}/${companyName}`);
    }

    byType(type: CompanyType): Observable<Response> {
        return this.http.get(`${this.resourceUrl.concat('/by-type')}/${type}`);
    }
}
