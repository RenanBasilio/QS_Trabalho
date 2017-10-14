import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Local } from './local.model';
import { LocalService } from './local.service';

@Component({
    selector: 'jhi-local-detail',
    templateUrl: './local-detail.component.html'
})
export class LocalDetailComponent implements OnInit, OnDestroy {

    local: Local;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private localService: LocalService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocals();
    }

    load(id) {
        this.localService.find(id).subscribe((local) => {
            this.local = local;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLocals() {
        this.eventSubscriber = this.eventManager.subscribe(
            'localListModification',
            (response) => this.load(this.local.id)
        );
    }
}
