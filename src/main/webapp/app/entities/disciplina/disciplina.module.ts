import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QsTrabalhoDisciplinaSharedModule } from '../../shared';
import {
    DisciplinaService,
    DisciplinaPopupService,
    DisciplinaComponent,
    DisciplinaDetailComponent,
    DisciplinaDialogComponent,
    DisciplinaPopupComponent,
    DisciplinaDeletePopupComponent,
    DisciplinaDeleteDialogComponent,
    disciplinaRoute,
    disciplinaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...disciplinaRoute,
    ...disciplinaPopupRoute,
];

@NgModule({
    imports: [
        QsTrabalhoDisciplinaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DisciplinaComponent,
        DisciplinaDetailComponent,
        DisciplinaDialogComponent,
        DisciplinaDeleteDialogComponent,
        DisciplinaPopupComponent,
        DisciplinaDeletePopupComponent,
    ],
    entryComponents: [
        DisciplinaComponent,
        DisciplinaDialogComponent,
        DisciplinaPopupComponent,
        DisciplinaDeleteDialogComponent,
        DisciplinaDeletePopupComponent,
    ],
    providers: [
        DisciplinaService,
        DisciplinaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaDisciplinaModule {}
