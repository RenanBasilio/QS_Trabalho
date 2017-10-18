import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProgramaComponent } from './programa.component';
import { ProgramaDetailComponent } from './programa-detail.component';
import { ProgramaPopupComponent } from './programa-dialog.component';
import { ProgramaDeletePopupComponent } from './programa-delete-dialog.component';

export const programaRoute: Routes = [
    {
        path: 'programa',
        component: ProgramaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.programa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'programa/:id',
        component: ProgramaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.programa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const programaPopupRoute: Routes = [
    {
        path: 'programa-new',
        component: ProgramaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.programa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'programa/:id/edit',
        component: ProgramaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.programa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'programa/:id/delete',
        component: ProgramaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'qsTrabalhoDisciplinaApp.programa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
