import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TimeSlot } from './time-slot.model';
import { TimeSlotService } from './time-slot.service';

@Component({
    selector: 'jhi-time-slot-detail',
    templateUrl: './time-slot-detail.component.html'
})
export class TimeSlotDetailComponent implements OnInit, OnDestroy {

    timeSlot: TimeSlot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private timeSlotService: TimeSlotService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTimeSlots();
    }

    load(id) {
        this.timeSlotService.find(id).subscribe((timeSlot) => {
            this.timeSlot = timeSlot;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTimeSlots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'timeSlotListModification',
            (response) => this.load(this.timeSlot.id)
        );
    }
}
