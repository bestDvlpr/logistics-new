import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CarModelDetailComponent } from '../../../../../../main/webapp/app/entities/car-model/car-model-detail.component';
import { CarModelService } from '../../../../../../main/webapp/app/entities/car-model/car-model.service';
import { CarModel } from '../../../../../../main/webapp/app/entities/car-model/car-model.model';

describe('Component Tests', () => {

    describe('CarModel Management Detail Component', () => {
        let comp: CarModelDetailComponent;
        let fixture: ComponentFixture<CarModelDetailComponent>;
        let service: CarModelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CarModelDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    CarModelService
                ]
            }).overrideComponent(CarModelDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CarModelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarModelService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CarModel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.carModel).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
