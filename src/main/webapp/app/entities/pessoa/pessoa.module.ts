import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QsTrabalhoDisciplinaSharedModule } from '../../shared';
import {
    PessoaService,
    PessoaPopupService,
    PessoaComponent,
    PessoaDetailComponent,
    PessoaDialogComponent,
    PessoaPopupComponent,
    PessoaDeletePopupComponent,
    PessoaDeleteDialogComponent,
    pessoaRoute,
    pessoaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...pessoaRoute,
    ...pessoaPopupRoute,
];

@NgModule({
    imports: [
        QsTrabalhoDisciplinaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PessoaComponent,
        PessoaDetailComponent,
        PessoaDialogComponent,
        PessoaDeleteDialogComponent,
        PessoaPopupComponent,
        PessoaDeletePopupComponent,
    ],
    entryComponents: [
        PessoaComponent,
        PessoaDialogComponent,
        PessoaPopupComponent,
        PessoaDeleteDialogComponent,
        PessoaDeletePopupComponent,
    ],
    providers: [
        PessoaService,
        PessoaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaPessoaModule {}
