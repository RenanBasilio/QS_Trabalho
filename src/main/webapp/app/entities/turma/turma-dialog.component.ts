import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Turma } from './turma.model';
import { TurmaPopupService } from './turma-popup.service';
import { TurmaService } from './turma.service';
import { Disciplina, DisciplinaService } from '../disciplina';
import { Local, LocalService } from '../local';
import { TimeSlot, TimeSlotService } from '../time-slot';
import { ResponseWrapper } from '../../shared';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
    selector: 'jhi-turma-dialog',
    templateUrl: './turma-dialog.component.html'
})
export class TurmaDialogComponent implements OnInit {

    turma: Turma;
    isSaving: boolean;

    disciplinas: Disciplina[];

    salas: Local[];

    timeslots: TimeSlot[];

    horarios: TimeSlot[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private turmaService: TurmaService,
        private disciplinaService: DisciplinaService,
        private localService: LocalService,
        private timeSlotService: TimeSlotService,
        private eventManager: JhiEventManager
    ) {
    }

    addTimeSlot() {
        if (this.horarios === undefined) {
            this.horarios = [];
        }

        this.horarios.push({});
    }

    removeTimeSlot(index) {
        console.log(index);
        this.horarios.splice(index, 1);
    }

    ngOnInit() {
        this.isSaving = false;
        this.disciplinaService
            .query({filter: 'turma-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.turma.disciplina || !this.turma.disciplina.id) {
                    this.disciplinas = res.json;
                } else {
                    this.disciplinaService
                        .find(this.turma.disciplina.id)
                        .subscribe((subRes: Disciplina) => {
                            this.disciplinas = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.localService
            .query({filter: 'turma-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.turma.sala || !this.turma.sala.id) {
                    this.salas = res.json;
                } else {
                    this.localService
                        .find(this.turma.sala.id)
                        .subscribe((subRes: Local) => {
                            this.salas = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.timeSlotService.query()
            .subscribe((res: ResponseWrapper) => { this.timeslots = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.horarios.length > 0) {
            this.horarios.forEach((timeslot) => {
                console.log(timeslot);
                const observable = this.timeSlotService.create(timeslot);
                console.log(observable);
            });
        }
        if (this.turma.id !== undefined) {
            this.subscribeToSaveResponse(
                this.turmaService.update(this.turma));
        } else {
            this.subscribeToSaveResponse(
                this.turmaService.create(this.turma));
        }
    }

    private subscribeToSaveResponse(result: Observable<Turma>) {
        result.subscribe((res: Turma) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Turma) {
        this.eventManager.broadcast({ name: 'turmaListModification', content: 'OK'});
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

    trackDisciplinaById(index: number, item: Disciplina) {
        return item.id;
    }

    trackLocalById(index: number, item: Local) {
        return item.id;
    }

    trackTimeSlotById(index: number, item: TimeSlot) {
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
    selector: 'jhi-turma-popup',
    template: ''
})
export class TurmaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private turmaPopupService: TurmaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.turmaPopupService
                    .open(TurmaDialogComponent as Component, params['id']);
            } else {
                this.turmaPopupService
                    .open(TurmaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
