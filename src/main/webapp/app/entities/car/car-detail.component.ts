import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Car } from './car.model';
import { CarService } from './car.service';

@Component({
    selector: 'jhi-car-detail',
    templateUrl: './car-detail.component.html'
})
export class CarDetailComponent implements OnInit, OnDestroy {

    car: Car;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carService: CarService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['car', 'carStatus']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.carService.find(id).subscribe(car => {
            this.car = car;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
