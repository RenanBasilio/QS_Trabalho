import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QsTrabalhoDisciplinaSharedModule } from '../../shared';
import {
    InscricaoService,
    InscricaoPopupService,
    InscricaoComponent,
    InscricaoDetailComponent,
    InscricaoDialogComponent,
    InscricaoPopupComponent,
    InscricaoDeletePopupComponent,
    InscricaoDeleteDialogComponent,
    inscricaoRoute,
    inscricaoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...inscricaoRoute,
    ...inscricaoPopupRoute,
];

@NgModule({
    imports: [
        QsTrabalhoDisciplinaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        InscricaoComponent,
        InscricaoDetailComponent,
        InscricaoDialogComponent,
        InscricaoDeleteDialogComponent,
        InscricaoPopupComponent,
        InscricaoDeletePopupComponent,
    ],
    entryComponents: [
        InscricaoComponent,
        InscricaoDialogComponent,
        InscricaoPopupComponent,
        InscricaoDeleteDialogComponent,
        InscricaoDeletePopupComponent,
    ],
    providers: [
        InscricaoService,
        InscricaoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaInscricaoModule {}
