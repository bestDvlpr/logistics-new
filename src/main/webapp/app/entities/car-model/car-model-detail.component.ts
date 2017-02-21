import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { CarModel } from './car-model.model';
import { CarModelService } from './car-model.service';

@Component({
    selector: 'jhi-car-model-detail',
    templateUrl: './car-model-detail.component.html'
})
export class CarModelDetailComponent implements OnInit, OnDestroy {

    carModel: CarModel;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private carModelService: CarModelService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['carModel']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.carModelService.find(id).subscribe(carModel => {
            this.carModel = carModel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
