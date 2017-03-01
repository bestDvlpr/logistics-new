import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { CarType } from './car-type.model';
import { CarTypeService } from './car-type.service';

@Component({
    selector: 'jhi-car-type-detail',
    templateUrl: './car-type-detail.component.html'
})
export class CarTypeDetailComponent implements OnInit, OnDestroy {

    carType: CarType;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carTypeService: CarTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['carType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.carTypeService.find(id).subscribe(carType => {
            this.carType = carType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
