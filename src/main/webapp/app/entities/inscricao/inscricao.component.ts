import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Inscricao } from './inscricao.model';
import { InscricaoService } from './inscricao.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-inscricao',
    templateUrl: './inscricao.component.html'
})
export class InscricaoComponent implements OnInit, OnDestroy {
inscricaos: Inscricao[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inscricaoService: InscricaoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.inscricaoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.inscricaos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInInscricaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Inscricao) {
        return item.id;
    }
    registerChangeInInscricaos() {
        this.eventSubscriber = this.eventManager.subscribe('inscricaoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
