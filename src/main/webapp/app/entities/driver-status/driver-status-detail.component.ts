import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { DriverStatus } from './driver-status.model';
import { DriverStatusService } from './driver-status.service';

@Component({
    selector: 'jhi-driver-status-detail',
    templateUrl: './driver-status-detail.component.html'
})
export class DriverStatusDetailComponent implements OnInit, OnDestroy {

    driverStatus: DriverStatus;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private driverStatusService: DriverStatusService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['driverStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.driverStatusService.find(id).subscribe(driverStatus => {
            this.driverStatus = driverStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
