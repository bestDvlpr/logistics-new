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
import { PaymentTypeDetailComponent } from '../../../../../../main/webapp/app/entities/payment-type/payment-type-detail.component';
import { PaymentTypeService } from '../../../../../../main/webapp/app/entities/payment-type/payment-type.service';
import { PaymentType } from '../../../../../../main/webapp/app/entities/payment-type/payment-type.model';

describe('Component Tests', () => {

    describe('PaymentType Management Detail Component', () => {
        let comp: PaymentTypeDetailComponent;
        let fixture: ComponentFixture<PaymentTypeDetailComponent>;
        let service: PaymentTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PaymentTypeDetailComponent],
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
                    PaymentTypeService
                ]
            }).overrideComponent(PaymentTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new PaymentType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.paymentType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
