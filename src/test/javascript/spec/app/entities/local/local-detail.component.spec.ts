/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QsTrabalhoDisciplinaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LocalDetailComponent } from '../../../../../../main/webapp/app/entities/local/local-detail.component';
import { LocalService } from '../../../../../../main/webapp/app/entities/local/local.service';
import { Local } from '../../../../../../main/webapp/app/entities/local/local.model';

describe('Component Tests', () => {

    describe('Local Management Detail Component', () => {
        let comp: LocalDetailComponent;
        let fixture: ComponentFixture<LocalDetailComponent>;
        let service: LocalService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QsTrabalhoDisciplinaTestModule],
                declarations: [LocalDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LocalService,
                    JhiEventManager
                ]
            }).overrideTemplate(LocalDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Local(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.local).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
