import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Disciplina } from './disciplina.model';
import { DisciplinaPopupService } from './disciplina-popup.service';
import { DisciplinaService } from './disciplina.service';
import { Professor, ProfessorService } from '../professor';
import { Programa, ProgramaService } from '../programa';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-disciplina-dialog',
    templateUrl: './disciplina-dialog.component.html'
})
export class DisciplinaDialogComponent implements OnInit {

    disciplina: Disciplina;
    isSaving: boolean;

    professors: Professor[];

    programas: Programa[];

    disciplinas: Disciplina[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private disciplinaService: DisciplinaService,
        private professorService: ProfessorService,
        private programaService: ProgramaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.professorService
            .query({filter: 'disciplina-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.disciplina.professor || !this.disciplina.professor.id) {
                    this.professors = res.json;
                } else {
                    this.professorService
                        .find(this.disciplina.professor.id)
                        .subscribe((subRes: Professor) => {
                            this.professors = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.programaService
            .query({filter: 'disciplina-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.disciplina.programa || !this.disciplina.programa.id) {
                    this.programas = res.json;
                } else {
                    this.programaService
                        .find(this.disciplina.programa.id)
                        .subscribe((subRes: Programa) => {
                            this.programas = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.disciplinaService.query()
            .subscribe((res: ResponseWrapper) => { this.disciplinas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.disciplina.id !== undefined) {
            this.subscribeToSaveResponse(
                this.disciplinaService.update(this.disciplina));
        } else {
            this.subscribeToSaveResponse(
                this.disciplinaService.create(this.disciplina));
        }
    }

    private subscribeToSaveResponse(result: Observable<Disciplina>) {
        result.subscribe((res: Disciplina) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Disciplina) {
        this.eventManager.broadcast({ name: 'disciplinaListModification', content: 'OK'});
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

    trackProfessorById(index: number, item: Professor) {
        return item.id;
    }

    trackProgramaById(index: number, item: Programa) {
        return item.id;
    }

    trackDisciplinaById(index: number, item: Disciplina) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-disciplina-popup',
    template: ''
})
export class DisciplinaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private disciplinaPopupService: DisciplinaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.disciplinaPopupService
                    .open(DisciplinaDialogComponent as Component, params['id']);
            } else {
                this.disciplinaPopupService
                    .open(DisciplinaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
