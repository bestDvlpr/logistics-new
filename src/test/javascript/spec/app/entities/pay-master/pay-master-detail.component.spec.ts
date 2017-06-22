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
import { PayMasterDetailComponent } from '../../../../../../main/webapp/app/entities/pay-master/pay-master-detail.component';
import { PayMasterService } from '../../../../../../main/webapp/app/entities/pay-master/pay-master.service';
import { PayMaster } from '../../../../../../main/webapp/app/entities/pay-master/pay-master.model';

describe('Component Tests', () => {

    describe('PayMaster Management Detail Component', () => {
        let comp: PayMasterDetailComponent;
        let fixture: ComponentFixture<PayMasterDetailComponent>;
        let service: PayMasterService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PayMasterDetailComponent],
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
                    PayMasterService
                ]
            }).overrideComponent(PayMasterDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayMasterDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayMasterService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new PayMaster(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.payMaster).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
