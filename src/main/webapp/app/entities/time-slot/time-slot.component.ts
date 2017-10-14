import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { TimeSlot } from './time-slot.model';
import { TimeSlotService } from './time-slot.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-time-slot',
    templateUrl: './time-slot.component.html'
})
export class TimeSlotComponent implements OnInit, OnDestroy {
timeSlots: TimeSlot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private timeSlotService: TimeSlotService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.timeSlotService.query().subscribe(
            (res: ResponseWrapper) => {
                this.timeSlots = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTimeSlots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TimeSlot) {
        return item.id;
    }
    registerChangeInTimeSlots() {
        this.eventSubscriber = this.eventManager.subscribe('timeSlotListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
