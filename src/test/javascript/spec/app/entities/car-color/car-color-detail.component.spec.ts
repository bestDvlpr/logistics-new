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
import { CarColorDetailComponent } from '../../../../../../main/webapp/app/entities/car-color/car-color-detail.component';
import { CarColorService } from '../../../../../../main/webapp/app/entities/car-color/car-color.service';
import { CarColor } from '../../../../../../main/webapp/app/entities/car-color/car-color.model';

describe('Component Tests', () => {

    describe('CarColor Management Detail Component', () => {
        let comp: CarColorDetailComponent;
        let fixture: ComponentFixture<CarColorDetailComponent>;
        let service: CarColorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CarColorDetailComponent],
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
                    CarColorService
                ]
            }).overrideComponent(CarColorDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CarColorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarColorService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new CarColor(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.carColor).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
