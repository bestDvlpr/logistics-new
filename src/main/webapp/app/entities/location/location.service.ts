import {Injectable} from "@angular/core";
import {BaseRequestOptions, Http, Response, URLSearchParams} from "@angular/http";
import {Observable} from "rxjs/Rx";

import {Location, LocationType} from "./location.model";
@Injectable()
export class LocationService {

    private resourceUrl = 'api/locations';

    constructor(private http: Http) {
    }

    create(location: Location): Observable<Location> {
        let copy: Location = Object.assign({}, location);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(location: Location): Observable<Location> {
        let copy: Location = Object.assign({}, location);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Location> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findChildren(id: number, req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/children/${id}`, options);
    }

    findCountries(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/countries`, options);
    }

    findChildList(id: number, req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/child-list/${id}`, options);
    }

    findCountryList(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/country-list`, options);
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            ;
    }

    getAll(): Observable<Location[]> {
        return this.http.get(this.resourceUrl.concat('/all')).map((res: Response) => { // TODO make it sorted by category
            return res.json();
        });
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
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

    findByType(type: LocationType) {
        return this.http.get(`${this.resourceUrl}/by-type/${type}`);
    }
}
