import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QsTrabalhoDisciplinaSharedModule } from '../../shared';
import {
    ProgramaService,
    ProgramaPopupService,
    ProgramaComponent,
    ProgramaDetailComponent,
    ProgramaDialogComponent,
    ProgramaPopupComponent,
    ProgramaDeletePopupComponent,
    ProgramaDeleteDialogComponent,
    programaRoute,
    programaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...programaRoute,
    ...programaPopupRoute,
];

@NgModule({
    imports: [
        QsTrabalhoDisciplinaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProgramaComponent,
        ProgramaDetailComponent,
        ProgramaDialogComponent,
        ProgramaDeleteDialogComponent,
        ProgramaPopupComponent,
        ProgramaDeletePopupComponent,
    ],
    entryComponents: [
        ProgramaComponent,
        ProgramaDialogComponent,
        ProgramaPopupComponent,
        ProgramaDeleteDialogComponent,
        ProgramaDeletePopupComponent,
    ],
    providers: [
        ProgramaService,
        ProgramaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaProgramaModule {}
