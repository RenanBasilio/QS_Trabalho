import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Turma } from './turma.model';
import { TurmaService } from './turma.service';

@Component({
    selector: 'jhi-turma-detail',
    templateUrl: './turma-detail.component.html'
})
export class TurmaDetailComponent implements OnInit, OnDestroy {

    turma: Turma;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private turmaService: TurmaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTurmas();
    }

    load(id) {
        this.turmaService.find(id).subscribe((turma) => {
            this.turma = turma;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTurmas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'turmaListModification',
            (response) => this.load(this.turma.id)
        );
    }
}
