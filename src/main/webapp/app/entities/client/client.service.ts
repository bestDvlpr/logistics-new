import {Injectable} from '@angular/core';
import {BaseRequestOptions, Http, Response, URLSearchParams} from '@angular/http';
import {Observable} from 'rxjs/Rx';

import {Client} from './client.model';
import {JhiDateUtils} from 'ng-jhipster';
@Injectable()
export class ClientService {

    private resourceUrl = 'api/clients';

    constructor(private http: Http, private dateUtils: JhiDateUtils) {
    }

    create(client: Client): Observable<Client> {
        const copy: Client = Object.assign({}, client);
        copy.regDate = this.dateUtils.toDate(client.regDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(client: Client): Observable<Client> {
        const copy: Client = Object.assign({}, client);

        copy.regDate = this.dateUtils.toDate(client.regDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Client> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.regDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.regDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
            ;
    }

    byPhoneNumber(phoneNumber: string): Observable<Client> {
        return this.http.get(`${this.resourceUrl}/by-phone/${phoneNumber}`).map((res: Response) => {
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].regDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].regDate);
        }
        res._body = jsonResponse;
        return res;
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
}
