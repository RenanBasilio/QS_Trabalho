import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Disciplina } from './disciplina.model';
import { DisciplinaService } from './disciplina.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-disciplina',
    templateUrl: './disciplina.component.html'
})
export class DisciplinaComponent implements OnInit, OnDestroy {
disciplinas: Disciplina[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private disciplinaService: DisciplinaService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.disciplinaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.disciplinas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDisciplinas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Disciplina) {
        return item.id;
    }
    registerChangeInDisciplinas() {
        this.eventSubscriber = this.eventManager.subscribe('disciplinaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
