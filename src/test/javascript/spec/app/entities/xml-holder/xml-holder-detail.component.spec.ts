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
import { XmlHolderDetailComponent } from '../../../../../../main/webapp/app/entities/xml-holder/xml-holder-detail.component';
import { XmlHolderService } from '../../../../../../main/webapp/app/entities/xml-holder/xml-holder.service';
import { XmlHolder } from '../../../../../../main/webapp/app/entities/xml-holder/xml-holder.model';

describe('Component Tests', () => {

    describe('XmlHolder Management Detail Component', () => {
        let comp: XmlHolderDetailComponent;
        let fixture: ComponentFixture<XmlHolderDetailComponent>;
        let service: XmlHolderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [XmlHolderDetailComponent],
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
                    XmlHolderService
                ]
            }).overrideComponent(XmlHolderDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(XmlHolderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(XmlHolderService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new XmlHolder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.xmlHolder).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
