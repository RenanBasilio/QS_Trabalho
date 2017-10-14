/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QsTrabalhoDisciplinaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProgramaDetailComponent } from '../../../../../../main/webapp/app/entities/programa/programa-detail.component';
import { ProgramaService } from '../../../../../../main/webapp/app/entities/programa/programa.service';
import { Programa } from '../../../../../../main/webapp/app/entities/programa/programa.model';

describe('Component Tests', () => {

    describe('Programa Management Detail Component', () => {
        let comp: ProgramaDetailComponent;
        let fixture: ComponentFixture<ProgramaDetailComponent>;
        let service: ProgramaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QsTrabalhoDisciplinaTestModule],
                declarations: [ProgramaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProgramaService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProgramaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProgramaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgramaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Programa(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.programa).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
