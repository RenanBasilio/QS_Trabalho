import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PessoaComponent } from './pessoa.component';
import { PessoaDetailComponent } from './pessoa-detail.component';
import { PessoaPopupComponent } from './pessoa-dialog.component';
import { PessoaDeletePopupComponent } from './pessoa-delete-dialog.component';

export const pessoaRoute: Routes = [
    {
        path: 'pessoa',
        component: PessoaComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.pessoa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pessoa/:id',
        component: PessoaDetailComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.pessoa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pessoaPopupRoute: Routes = [
    {
        path: 'pessoa-new',
        component: PessoaPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.pessoa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pessoa/:id/edit',
        component: PessoaPopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.pessoa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pessoa/:id/delete',
        component: PessoaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_SECRETARY'],
            pageTitle: 'qsTrabalhoDisciplinaApp.pessoa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
