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
import { SellerDetailComponent } from '../../../../../../main/webapp/app/entities/seller/seller-detail.component';
import { SellerService } from '../../../../../../main/webapp/app/entities/seller/seller.service';
import { Seller } from '../../../../../../main/webapp/app/entities/seller/seller.model';

describe('Component Tests', () => {

    describe('Seller Management Detail Component', () => {
        let comp: SellerDetailComponent;
        let fixture: ComponentFixture<SellerDetailComponent>;
        let service: SellerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SellerDetailComponent],
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
                    SellerService
                ]
            }).overrideComponent(SellerDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SellerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SellerService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Seller(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.seller).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
