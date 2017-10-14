import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Programa } from './programa.model';
import { ProgramaService } from './programa.service';

@Component({
    selector: 'jhi-programa-detail',
    templateUrl: './programa-detail.component.html'
})
export class ProgramaDetailComponent implements OnInit, OnDestroy {

    programa: Programa;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private programaService: ProgramaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProgramas();
    }

    load(id) {
        this.programaService.find(id).subscribe((programa) => {
            this.programa = programa;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProgramas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'programaListModification',
            (response) => this.load(this.programa.id)
        );
    }
}
