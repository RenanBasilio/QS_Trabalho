import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Programa } from './programa.model';
import { ProgramaPopupService } from './programa-popup.service';
import { ProgramaService } from './programa.service';

@Component({
    selector: 'jhi-programa-dialog',
    templateUrl: './programa-dialog.component.html'
})
export class ProgramaDialogComponent implements OnInit {

    programa: Programa;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private programaService: ProgramaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.programa.id !== undefined) {
            this.subscribeToSaveResponse(
                this.programaService.update(this.programa));
        } else {
            this.subscribeToSaveResponse(
                this.programaService.create(this.programa));
        }
    }

    private subscribeToSaveResponse(result: Observable<Programa>) {
        result.subscribe((res: Programa) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Programa) {
        this.eventManager.broadcast({ name: 'programaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-programa-popup',
    template: ''
})
export class ProgramaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private programaPopupService: ProgramaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.programaPopupService
                    .open(ProgramaDialogComponent as Component, params['id']);
            } else {
                this.programaPopupService
                    .open(ProgramaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
