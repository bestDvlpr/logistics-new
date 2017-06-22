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
import { CarTypeDetailComponent } from '../../../../../../main/webapp/app/entities/car-type/car-type-detail.component';
import { CarTypeService } from '../../../../../../main/webapp/app/entities/car-type/car-type.service';
import { CarType } from '../../../../../../main/webapp/app/entities/car-type/car-type.model';

describe('Component Tests', () => {

    describe('CarType Management Detail Component', () => {
        let comp: CarTypeDetailComponent;
        let fixture: ComponentFixture<CarTypeDetailComponent>;
        let service: CarTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CarTypeDetailComponent],
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
                    CarTypeService
                ]
            }).overrideComponent(CarTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CarTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new CarType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.carType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
