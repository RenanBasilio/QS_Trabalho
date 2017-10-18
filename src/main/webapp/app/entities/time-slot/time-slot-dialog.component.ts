import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TimeSlot } from './time-slot.model';
import { TimeSlotPopupService } from './time-slot-popup.service';
import { TimeSlotService } from './time-slot.service';

@Component({
    selector: 'jhi-time-slot-dialog',
    templateUrl: './time-slot-dialog.component.html'
})
export class TimeSlotDialogComponent implements OnInit {

    timeSlot: TimeSlot;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private timeSlotService: TimeSlotService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.timeSlot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.timeSlotService.update(this.timeSlot));
        } else {
            this.subscribeToSaveResponse(
                this.timeSlotService.create(this.timeSlot));
        }
    }

    private subscribeToSaveResponse(result: Observable<TimeSlot>) {
        result.subscribe((res: TimeSlot) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TimeSlot) {
        this.eventManager.broadcast({ name: 'timeSlotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-time-slot-popup',
    template: ''
})
export class TimeSlotPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timeSlotPopupService: TimeSlotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.timeSlotPopupService
                    .open(TimeSlotDialogComponent as Component, params['id']);
            } else {
                this.timeSlotPopupService
                    .open(TimeSlotDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
