import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Disciplina } from './disciplina.model';
import { DisciplinaService } from './disciplina.service';

@Component({
    selector: 'jhi-disciplina-detail',
    templateUrl: './disciplina-detail.component.html'
})
export class DisciplinaDetailComponent implements OnInit, OnDestroy {

    disciplina: Disciplina;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private disciplinaService: DisciplinaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDisciplinas();
    }

    load(id) {
        this.disciplinaService.find(id).subscribe((disciplina) => {
            this.disciplina = disciplina;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDisciplinas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'disciplinaListModification',
            (response) => this.load(this.disciplina.id)
        );
    }
}
