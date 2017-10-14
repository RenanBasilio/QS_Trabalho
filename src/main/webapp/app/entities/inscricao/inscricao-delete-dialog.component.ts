import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Inscricao } from './inscricao.model';
import { InscricaoPopupService } from './inscricao-popup.service';
import { InscricaoService } from './inscricao.service';

@Component({
    selector: 'jhi-inscricao-delete-dialog',
    templateUrl: './inscricao-delete-dialog.component.html'
})
export class InscricaoDeleteDialogComponent {

    inscricao: Inscricao;

    constructor(
        private inscricaoService: InscricaoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inscricaoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inscricaoListModification',
                content: 'Deleted an inscricao'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inscricao-delete-popup',
    template: ''
})
export class InscricaoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inscricaoPopupService: InscricaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inscricaoPopupService
                .open(InscricaoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
