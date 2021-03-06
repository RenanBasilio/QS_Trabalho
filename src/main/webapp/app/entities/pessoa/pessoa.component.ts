import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Pessoa } from './pessoa.model';
import { PessoaService } from './pessoa.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pessoa',
    templateUrl: './pessoa.component.html'
})
export class PessoaComponent implements OnInit, OnDestroy {
pessoas: Pessoa[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pessoaService: PessoaService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pessoaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pessoas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPessoas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pessoa) {
        return item.id;
    }
    registerChangeInPessoas() {
        this.eventSubscriber = this.eventManager.subscribe('pessoaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
