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
import { PayTypeDetailComponent } from '../../../../../../main/webapp/app/entities/pay-type/pay-type-detail.component';
import { PayTypeService } from '../../../../../../main/webapp/app/entities/pay-type/pay-type.service';
import { PayType } from '../../../../../../main/webapp/app/entities/pay-type/pay-type.model';

describe('Component Tests', () => {

    describe('PayType Management Detail Component', () => {
        let comp: PayTypeDetailComponent;
        let fixture: ComponentFixture<PayTypeDetailComponent>;
        let service: PayTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PayTypeDetailComponent],
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
                    PayTypeService
                ]
            }).overrideComponent(PayTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PayTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PayTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new PayType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.payType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
