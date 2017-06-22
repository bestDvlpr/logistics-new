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
import { ShopDetailComponent } from '../../../../../../main/webapp/app/entities/shop/shop-detail.component';
import { ShopService } from '../../../../../../main/webapp/app/entities/shop/shop.service';
import { Shop } from '../../../../../../main/webapp/app/entities/shop/shop.model';

describe('Component Tests', () => {

    describe('Shop Management Detail Component', () => {
        let comp: ShopDetailComponent;
        let fixture: ComponentFixture<ShopDetailComponent>;
        let service: ShopService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ShopDetailComponent],
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
                    ShopService
                ]
            }).overrideComponent(ShopDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ShopDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShopService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Shop(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.shop).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
