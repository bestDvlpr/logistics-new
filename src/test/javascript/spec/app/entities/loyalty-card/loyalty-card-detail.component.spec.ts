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
import { LoyaltyCardDetailComponent } from '../../../../../../main/webapp/app/entities/loyalty-card/loyalty-card-detail.component';
import { LoyaltyCardService } from '../../../../../../main/webapp/app/entities/loyalty-card/loyalty-card.service';
import { LoyaltyCard } from '../../../../../../main/webapp/app/entities/loyalty-card/loyalty-card.model';

describe('Component Tests', () => {

    describe('LoyaltyCard Management Detail Component', () => {
        let comp: LoyaltyCardDetailComponent;
        let fixture: ComponentFixture<LoyaltyCardDetailComponent>;
        let service: LoyaltyCardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [LoyaltyCardDetailComponent],
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
                    LoyaltyCardService
                ]
            }).overrideComponent(LoyaltyCardDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LoyaltyCardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoyaltyCardService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new LoyaltyCard(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.loyaltyCard).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
