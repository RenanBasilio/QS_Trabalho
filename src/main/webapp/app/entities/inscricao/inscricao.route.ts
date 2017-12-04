import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { InscricaoComponent } from './inscricao.component';
import { InscricaoDetailComponent } from './inscricao-detail.component';
import { InscricaoPopupComponent } from './inscricao-dialog.component';
import { InscricaoDeletePopupComponent } from './inscricao-delete-dialog.component';

export const inscricaoRoute: Routes = [
    {
        path: 'inscricao',
        component: InscricaoComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_STUDENT', 'ROLE_PROFESSOR'],
            pageTitle: 'qsTrabalhoDisciplinaApp.inscricao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inscricao/:id',
        component: InscricaoDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_STUDENT', 'ROLE_PROFESSOR'],
            pageTitle: 'qsTrabalhoDisciplinaApp.inscricao.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inscricaoPopupRoute: Routes = [
    {
        path: 'inscricao-new',
        component: InscricaoPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_STUDENT'],
            pageTitle: 'qsTrabalhoDisciplinaApp.inscricao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inscricao/:id/edit',
        component: InscricaoPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_STUDENT', 'ROLE_PROFESSOR'],
            pageTitle: 'qsTrabalhoDisciplinaApp.inscricao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inscricao/:id/delete',
        component: InscricaoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY', 'ROLE_STUDENT'],
            pageTitle: 'qsTrabalhoDisciplinaApp.inscricao.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
