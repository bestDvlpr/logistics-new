import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Driver } from './driver.model';
import { DriverService } from './driver.service';

@Component({
    selector: 'jhi-driver-detail',
    templateUrl: './driver-detail.component.html'
})
export class DriverDetailComponent implements OnInit, OnDestroy {

    driver: Driver;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private driverService: DriverService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['driver']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.driverService.find(id).subscribe(driver => {
            this.driver = driver;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
