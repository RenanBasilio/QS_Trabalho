import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QsTrabalhoDisciplinaSharedModule } from '../../shared';
import {
    LocalService,
    LocalPopupService,
    LocalComponent,
    LocalDetailComponent,
    LocalDialogComponent,
    LocalPopupComponent,
    LocalDeletePopupComponent,
    LocalDeleteDialogComponent,
    localRoute,
    localPopupRoute,
} from './';

const ENTITY_STATES = [
    ...localRoute,
    ...localPopupRoute,
];

@NgModule({
    imports: [
        QsTrabalhoDisciplinaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LocalComponent,
        LocalDetailComponent,
        LocalDialogComponent,
        LocalDeleteDialogComponent,
        LocalPopupComponent,
        LocalDeletePopupComponent,
    ],
    entryComponents: [
        LocalComponent,
        LocalDialogComponent,
        LocalPopupComponent,
        LocalDeleteDialogComponent,
        LocalDeletePopupComponent,
    ],
    providers: [
        LocalService,
        LocalPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaLocalModule {}
