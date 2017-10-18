import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TimeSlot } from './time-slot.model';
import { TimeSlotPopupService } from './time-slot-popup.service';
import { TimeSlotService } from './time-slot.service';

@Component({
    selector: 'jhi-time-slot-delete-dialog',
    templateUrl: './time-slot-delete-dialog.component.html'
})
export class TimeSlotDeleteDialogComponent {

    timeSlot: TimeSlot;

    constructor(
        private timeSlotService: TimeSlotService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.timeSlotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'timeSlotListModification',
                content: 'Deleted an timeSlot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-time-slot-delete-popup',
    template: ''
})
export class TimeSlotDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private timeSlotPopupService: TimeSlotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.timeSlotPopupService
                .open(TimeSlotDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
