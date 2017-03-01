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
import { ProductEntryDetailComponent } from '../../../../../../main/webapp/app/entities/product-entry/product-entry-detail.component';
import { ProductEntryService } from '../../../../../../main/webapp/app/entities/product-entry/product-entry.service';
import { ProductEntry } from '../../../../../../main/webapp/app/entities/product-entry/product-entry.model';

describe('Component Tests', () => {

    describe('ProductEntry Management Detail Component', () => {
        let comp: ProductEntryDetailComponent;
        let fixture: ComponentFixture<ProductEntryDetailComponent>;
        let service: ProductEntryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ProductEntryDetailComponent],
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
                    ProductEntryService
                ]
            }).overrideComponent(ProductEntryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductEntryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductEntryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new ProductEntry(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productEntry).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
