/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { QsTrabalhoDisciplinaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TimeSlotDetailComponent } from '../../../../../../main/webapp/app/entities/time-slot/time-slot-detail.component';
import { TimeSlotService } from '../../../../../../main/webapp/app/entities/time-slot/time-slot.service';
import { TimeSlot } from '../../../../../../main/webapp/app/entities/time-slot/time-slot.model';

describe('Component Tests', () => {

    describe('TimeSlot Management Detail Component', () => {
        let comp: TimeSlotDetailComponent;
        let fixture: ComponentFixture<TimeSlotDetailComponent>;
        let service: TimeSlotService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [QsTrabalhoDisciplinaTestModule],
                declarations: [TimeSlotDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TimeSlotService,
                    JhiEventManager
                ]
            }).overrideTemplate(TimeSlotDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TimeSlotDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TimeSlotService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TimeSlot(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.timeSlot).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
