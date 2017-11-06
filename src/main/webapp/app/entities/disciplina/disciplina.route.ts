import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DisciplinaComponent } from './disciplina.component';
import { DisciplinaDetailComponent } from './disciplina-detail.component';
import { DisciplinaPopupComponent } from './disciplina-dialog.component';
import { DisciplinaDeletePopupComponent } from './disciplina-delete-dialog.component';

export const disciplinaRoute: Routes = [
    {
        path: 'disciplina',
        component: DisciplinaComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.disciplina.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'disciplina/:id',
        component: DisciplinaDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.disciplina.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const disciplinaPopupRoute: Routes = [
    {
        path: 'disciplina-new',
        component: DisciplinaPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.disciplina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disciplina/:id/edit',
        component: DisciplinaPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.disciplina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'disciplina/:id/delete',
        component: DisciplinaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.disciplina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
