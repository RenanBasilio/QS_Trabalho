import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Local } from './local.model';
import { LocalPopupService } from './local-popup.service';
import { LocalService } from './local.service';

@Component({
    selector: 'jhi-local-dialog',
    templateUrl: './local-dialog.component.html'
})
export class LocalDialogComponent implements OnInit {

    local: Local;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private localService: LocalService,
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
        if (this.local.id !== undefined) {
            this.subscribeToSaveResponse(
                this.localService.update(this.local));
        } else {
            this.subscribeToSaveResponse(
                this.localService.create(this.local));
        }
    }

    private subscribeToSaveResponse(result: Observable<Local>) {
        result.subscribe((res: Local) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Local) {
        this.eventManager.broadcast({ name: 'localListModification', content: 'OK'});
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
    selector: 'jhi-local-popup',
    template: ''
})
export class LocalPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private localPopupService: LocalPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.localPopupService
                    .open(LocalDialogComponent as Component, params['id']);
            } else {
                this.localPopupService
                    .open(LocalDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
