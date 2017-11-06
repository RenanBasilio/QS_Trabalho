import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LocalComponent } from './local.component';
import { LocalDetailComponent } from './local-detail.component';
import { LocalPopupComponent } from './local-dialog.component';
import { LocalDeletePopupComponent } from './local-delete-dialog.component';

export const localRoute: Routes = [
    {
        path: 'local',
        component: LocalComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.local.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'local/:id',
        component: LocalDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.local.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const localPopupRoute: Routes = [
    {
        path: 'local-new',
        component: LocalPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.local.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'local/:id/edit',
        component: LocalPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.local.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'local/:id/delete',
        component: LocalDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.local.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
