import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Inscricao } from './inscricao.model';
import { InscricaoPopupService } from './inscricao-popup.service';
import { InscricaoService } from './inscricao.service';
import { Aluno, AlunoService } from '../aluno';
import { Turma, TurmaService } from '../turma';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inscricao-dialog',
    templateUrl: './inscricao-dialog.component.html'
})
export class InscricaoDialogComponent implements OnInit {

    inscricao: Inscricao;
    isSaving: boolean;

    alunos: Aluno[];

    turmas: Turma[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private inscricaoService: InscricaoService,
        private alunoService: AlunoService,
        private turmaService: TurmaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.alunoService
            .query({filter: 'inscricao-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.inscricao.aluno || !this.inscricao.aluno.id) {
                    this.alunos = res.json;
                } else {
                    this.alunoService
                        .find(this.inscricao.aluno.id)
                        .subscribe((subRes: Aluno) => {
                            this.alunos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.turmaService.query()
            .subscribe((res: ResponseWrapper) => { this.turmas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inscricao.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inscricaoService.update(this.inscricao));
        } else {
            this.subscribeToSaveResponse(
                this.inscricaoService.create(this.inscricao));
        }
    }

    private subscribeToSaveResponse(result: Observable<Inscricao>) {
        result.subscribe((res: Inscricao) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Inscricao) {
        this.eventManager.broadcast({ name: 'inscricaoListModification', content: 'OK'});
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

    trackAlunoById(index: number, item: Aluno) {
        return item.id;
    }

    trackTurmaById(index: number, item: Turma) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-inscricao-popup',
    template: ''
})
export class InscricaoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inscricaoPopupService: InscricaoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inscricaoPopupService
                    .open(InscricaoDialogComponent as Component, params['id']);
            } else {
                this.inscricaoPopupService
                    .open(InscricaoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
