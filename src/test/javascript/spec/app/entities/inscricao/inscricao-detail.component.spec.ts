/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QsTrabalhoDisciplinaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InscricaoDetailComponent } from '../../../../../../main/webapp/app/entities/inscricao/inscricao-detail.component';
import { InscricaoService } from '../../../../../../main/webapp/app/entities/inscricao/inscricao.service';
import { Inscricao } from '../../../../../../main/webapp/app/entities/inscricao/inscricao.model';

describe('Component Tests', () => {

    describe('Inscricao Management Detail Component', () => {
        let comp: InscricaoDetailComponent;
        let fixture: ComponentFixture<InscricaoDetailComponent>;
        let service: InscricaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QsTrabalhoDisciplinaTestModule],
                declarations: [InscricaoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    InscricaoService,
                    JhiEventManager
                ]
            }).overrideTemplate(InscricaoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InscricaoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InscricaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Inscricao(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.inscricao).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
