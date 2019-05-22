import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DirectApplication } from 'app/shared/model/direct-application.model';
import { DirectApplicationService } from './direct-application.service';
import { DirectApplicationComponent } from './direct-application.component';
import { DirectApplicationDetailComponent } from './direct-application-detail.component';
import { DirectApplicationUpdateComponent } from './direct-application-update.component';
import { DirectApplicationDeletePopupComponent } from './direct-application-delete-dialog.component';
import { IDirectApplication } from 'app/shared/model/direct-application.model';

@Injectable({ providedIn: 'root' })
export class DirectApplicationResolve implements Resolve<IDirectApplication> {
    constructor(private service: DirectApplicationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDirectApplication> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DirectApplication>) => response.ok),
                map((directApplication: HttpResponse<DirectApplication>) => directApplication.body)
            );
        }
        return of(new DirectApplication());
    }
}

export const directApplicationRoute: Routes = [
    {
        path: '',
        component: DirectApplicationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.directApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DirectApplicationDetailComponent,
        resolve: {
            directApplication: DirectApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.directApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DirectApplicationUpdateComponent,
        resolve: {
            directApplication: DirectApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.directApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DirectApplicationUpdateComponent,
        resolve: {
            directApplication: DirectApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.directApplication.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const directApplicationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DirectApplicationDeletePopupComponent,
        resolve: {
            directApplication: DirectApplicationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.directApplication.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
