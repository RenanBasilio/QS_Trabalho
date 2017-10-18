import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Aluno } from './aluno.model';
import { AlunoPopupService } from './aluno-popup.service';
import { AlunoService } from './aluno.service';
import { Pessoa, PessoaService } from '../pessoa';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-aluno-dialog',
    templateUrl: './aluno-dialog.component.html'
})
export class AlunoDialogComponent implements OnInit {

    aluno: Aluno;
    isSaving: boolean;

    pessoas: Pessoa[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private alunoService: AlunoService,
        private pessoaService: PessoaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pessoaService
            .query({filter: 'aluno-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.aluno.pessoa || !this.aluno.pessoa.id) {
                    this.pessoas = res.json;
                } else {
                    this.pessoaService
                        .find(this.aluno.pessoa.id)
                        .subscribe((subRes: Pessoa) => {
                            this.pessoas = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aluno.id !== undefined) {
            this.subscribeToSaveResponse(
                this.alunoService.update(this.aluno));
        } else {
            this.subscribeToSaveResponse(
                this.alunoService.create(this.aluno));
        }
    }

    private subscribeToSaveResponse(result: Observable<Aluno>) {
        result.subscribe((res: Aluno) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Aluno) {
        this.eventManager.broadcast({ name: 'alunoListModification', content: 'OK'});
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

    trackPessoaById(index: number, item: Pessoa) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-aluno-popup',
    template: ''
})
export class AlunoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private alunoPopupService: AlunoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.alunoPopupService
                    .open(AlunoDialogComponent as Component, params['id']);
            } else {
                this.alunoPopupService
                    .open(AlunoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
