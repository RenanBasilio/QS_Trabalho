import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QsTrabalhoDisciplinaSharedModule } from '../../shared';
import {
    TimeSlotService,
    TimeSlotPopupService,
    TimeSlotComponent,
    TimeSlotDetailComponent,
    TimeSlotDialogComponent,
    TimeSlotPopupComponent,
    TimeSlotDeletePopupComponent,
    TimeSlotDeleteDialogComponent,
    timeSlotRoute,
    timeSlotPopupRoute,
} from './';

const ENTITY_STATES = [
    ...timeSlotRoute,
    ...timeSlotPopupRoute,
];

@NgModule({
    imports: [
        QsTrabalhoDisciplinaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TimeSlotComponent,
        TimeSlotDetailComponent,
        TimeSlotDialogComponent,
        TimeSlotDeleteDialogComponent,
        TimeSlotPopupComponent,
        TimeSlotDeletePopupComponent,
    ],
    entryComponents: [
        TimeSlotComponent,
        TimeSlotDialogComponent,
        TimeSlotPopupComponent,
        TimeSlotDeleteDialogComponent,
        TimeSlotDeletePopupComponent,
    ],
    providers: [
        TimeSlotService,
        TimeSlotPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QsTrabalhoDisciplinaTimeSlotModule {}
