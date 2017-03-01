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
import { LocationTypeDetailComponent } from '../../../../../../main/webapp/app/entities/location-type/location-type-detail.component';
import { LocationTypeService } from '../../../../../../main/webapp/app/entities/location-type/location-type.service';
import { LocationType } from '../../../../../../main/webapp/app/entities/location-type/location-type.model';

describe('Component Tests', () => {

    describe('LocationType Management Detail Component', () => {
        let comp: LocationTypeDetailComponent;
        let fixture: ComponentFixture<LocationTypeDetailComponent>;
        let service: LocationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [LocationTypeDetailComponent],
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
                    LocationTypeService
                ]
            }).overrideComponent(LocationTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new LocationType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.locationType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
