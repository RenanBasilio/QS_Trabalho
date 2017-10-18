import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Inscricao } from './inscricao.model';
import { InscricaoService } from './inscricao.service';

@Component({
    selector: 'jhi-inscricao-detail',
    templateUrl: './inscricao-detail.component.html'
})
export class InscricaoDetailComponent implements OnInit, OnDestroy {

    inscricao: Inscricao;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inscricaoService: InscricaoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInscricaos();
    }

    load(id) {
        this.inscricaoService.find(id).subscribe((inscricao) => {
            this.inscricao = inscricao;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInscricaos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inscricaoListModification',
            (response) => this.load(this.inscricao.id)
        );
    }
}
