import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Programa } from './programa.model';
import { ProgramaPopupService } from './programa-popup.service';
import { ProgramaService } from './programa.service';

@Component({
    selector: 'jhi-programa-delete-dialog',
    templateUrl: './programa-delete-dialog.component.html'
})
export class ProgramaDeleteDialogComponent {

    programa: Programa;

    constructor(
        private programaService: ProgramaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.programaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'programaListModification',
                content: 'Deleted an programa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-programa-delete-popup',
    template: ''
})
export class ProgramaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private programaPopupService: ProgramaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.programaPopupService
                .open(ProgramaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
