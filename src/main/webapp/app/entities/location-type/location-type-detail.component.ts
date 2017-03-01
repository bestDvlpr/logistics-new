import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { LocationType } from './location-type.model';
import { LocationTypeService } from './location-type.service';

@Component({
    selector: 'jhi-location-type-detail',
    templateUrl: './location-type-detail.component.html'
})
export class LocationTypeDetailComponent implements OnInit, OnDestroy {

    locationType: LocationType;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private locationTypeService: LocationTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['locationType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.locationTypeService.find(id).subscribe(locationType => {
            this.locationType = locationType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
