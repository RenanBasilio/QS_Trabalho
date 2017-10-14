/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QsTrabalhoDisciplinaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DisciplinaDetailComponent } from '../../../../../../main/webapp/app/entities/disciplina/disciplina-detail.component';
import { DisciplinaService } from '../../../../../../main/webapp/app/entities/disciplina/disciplina.service';
import { Disciplina } from '../../../../../../main/webapp/app/entities/disciplina/disciplina.model';

describe('Component Tests', () => {

    describe('Disciplina Management Detail Component', () => {
        let comp: DisciplinaDetailComponent;
        let fixture: ComponentFixture<DisciplinaDetailComponent>;
        let service: DisciplinaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QsTrabalhoDisciplinaTestModule],
                declarations: [DisciplinaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DisciplinaService,
                    JhiEventManager
                ]
            }).overrideTemplate(DisciplinaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DisciplinaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DisciplinaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Disciplina(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.disciplina).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
