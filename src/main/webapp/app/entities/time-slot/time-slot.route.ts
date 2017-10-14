import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TimeSlotComponent } from './time-slot.component';
import { TimeSlotDetailComponent } from './time-slot-detail.component';
import { TimeSlotPopupComponent } from './time-slot-dialog.component';
import { TimeSlotDeletePopupComponent } from './time-slot-delete-dialog.component';

export const timeSlotRoute: Routes = [
    {
        path: 'time-slot',
        component: TimeSlotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.timeSlot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'time-slot/:id',
        component: TimeSlotDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.timeSlot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const timeSlotPopupRoute: Routes = [
    {
        path: 'time-slot-new',
        component: TimeSlotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.timeSlot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'time-slot/:id/edit',
        component: TimeSlotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.timeSlot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'time-slot/:id/delete',
        component: TimeSlotDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.timeSlot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
