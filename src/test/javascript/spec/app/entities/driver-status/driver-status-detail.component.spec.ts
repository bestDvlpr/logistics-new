import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DriverStatusDetailComponent } from '../../../../../../main/webapp/app/entities/driver-status/driver-status-detail.component';
import { DriverStatusService } from '../../../../../../main/webapp/app/entities/driver-status/driver-status.service';
import { DriverStatus } from '../../../../../../main/webapp/app/entities/driver-status/driver-status.model';

describe('Component Tests', () => {

    describe('DriverStatus Management Detail Component', () => {
        let comp: DriverStatusDetailComponent;
        let fixture: ComponentFixture<DriverStatusDetailComponent>;
        let service: DriverStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [DriverStatusDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
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
                    DriverStatusService
                ]
            }).overrideComponent(DriverStatusDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DriverStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DriverStatusService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new DriverStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.driverStatus).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
