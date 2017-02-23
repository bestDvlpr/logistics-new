import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Location } from './location.model';
import { LocationService } from './location.service';

@Component({
    selector: 'jhi-location-detail',
    templateUrl: './location-detail.component.html'
})
export class LocationDetailComponent implements OnInit, OnDestroy {

    location: Location;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private locationService: LocationService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['location']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.locationService.find(id).subscribe(location => {
            this.location = location;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
