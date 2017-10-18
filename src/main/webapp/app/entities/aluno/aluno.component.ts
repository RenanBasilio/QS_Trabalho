import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Aluno } from './aluno.model';
import { AlunoService } from './aluno.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-aluno',
    templateUrl: './aluno.component.html'
})
export class AlunoComponent implements OnInit, OnDestroy {
alunos: Aluno[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private alunoService: AlunoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.alunoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.alunos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAlunos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Aluno) {
        return item.id;
    }
    registerChangeInAlunos() {
        this.eventSubscriber = this.eventManager.subscribe('alunoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
