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
import { ReceiptStatusDetailComponent } from '../../../../../../main/webapp/app/entities/receipt-status/receipt-status-detail.component';
import { ReceiptStatusService } from '../../../../../../main/webapp/app/entities/receipt-status/receipt-status.service';
import { ReceiptStatus } from '../../../../../../main/webapp/app/entities/receipt-status/receipt-status.model';

describe('Component Tests', () => {

    describe('ReceiptStatus Management Detail Component', () => {
        let comp: ReceiptStatusDetailComponent;
        let fixture: ComponentFixture<ReceiptStatusDetailComponent>;
        let service: ReceiptStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ReceiptStatusDetailComponent],
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
                    ReceiptStatusService
                ]
            }).overrideComponent(ReceiptStatusDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReceiptStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReceiptStatusService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new ReceiptStatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.receiptStatus).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
