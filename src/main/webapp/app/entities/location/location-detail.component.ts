import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {JhiAlertService, JhiEventManager, JhiPaginationUtil, JhiParseLinks} from 'ng-jhipster';
import {Location} from './location.model';
import {LocationService} from './location.service';
import {Response} from '@angular/http';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';
import {ITEMS_PER_PAGE, Principal} from '../../shared';
import {Subscription} from 'rxjs';
import {JhiLanguageHelper} from '../../shared/language/language.helper';

@Component({
    selector: 'jhi-location-detail',
    templateUrl: './location-detail.component.html'
})
export class LocationDetailComponent implements OnInit, OnDestroy {

    location: Location;
    childLocations: Location[];
    private subscription: any;
    itemsPerPage: any;
    page: any;
    totalItems: any;
    links: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    previousPage: any;
    routeData: any;
    eventSubscriber: Subscription;
    parentId: number;
    languages: any[];

    constructor(private languageHelper: JhiLanguageHelper,
                private locationService: LocationService,
                private route: ActivatedRoute,
                private parseLinks: JhiParseLinks,
                private alertService: JhiAlertService,
                private principal: Principal,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private eventManager: JhiEventManager,
                private paginationUtil: JhiPaginationUtil,
                private paginationConfig: PaginationConfig) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data['pagingParams'].page;
            this.previousPage = data['pagingParams'].page;
            this.reverse = data['pagingParams'].ascending;
            this.predicate = data['pagingParams'].predicate;
        });
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadChildren(params['id']);
            this.parentId = params['id'];
        });
        this.childLocations = [];
        this.registerChangeInLocations();
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });
    }

    load(id) {
        this.locationService.find(id).subscribe((location) => {
            this.location = location;
        });
    }

    loadChildren(id: number) {
        this.locationService.findChildren(id, {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: Response) => this.onSuccess(res.json(), res.headers),
            (res: Response) => this.onError(res.json())
        );
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.childLocations = data;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    trackId(index: number, item: Location) {
        return item.id;
    }

    transition() {
        this.router.navigate(['/location/:id'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.loadChildren(params['id']);
        });
    }

    registerChangeInLocations() {
        this.eventSubscriber = this.eventManager.subscribe('locationListModification', (response) => this.loadChildren(this.parentId));
    }
}
