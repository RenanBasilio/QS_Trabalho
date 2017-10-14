import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pessoa } from './pessoa.model';
import { PessoaPopupService } from './pessoa-popup.service';
import { PessoaService } from './pessoa.service';

@Component({
    selector: 'jhi-pessoa-delete-dialog',
    templateUrl: './pessoa-delete-dialog.component.html'
})
export class PessoaDeleteDialogComponent {

    pessoa: Pessoa;

    constructor(
        private pessoaService: PessoaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pessoaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pessoaListModification',
                content: 'Deleted an pessoa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pessoa-delete-popup',
    template: ''
})
export class PessoaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pessoaPopupService: PessoaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pessoaPopupService
                .open(PessoaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
