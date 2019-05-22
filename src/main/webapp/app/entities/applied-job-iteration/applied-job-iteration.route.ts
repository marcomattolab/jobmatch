import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';
import { AppliedJobIterationService } from './applied-job-iteration.service';
import { AppliedJobIterationComponent } from './applied-job-iteration.component';
import { AppliedJobIterationDetailComponent } from './applied-job-iteration-detail.component';
import { AppliedJobIterationUpdateComponent } from './applied-job-iteration-update.component';
import { AppliedJobIterationDeletePopupComponent } from './applied-job-iteration-delete-dialog.component';
import { IAppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';

@Injectable({ providedIn: 'root' })
export class AppliedJobIterationResolve implements Resolve<IAppliedJobIteration> {
    constructor(private service: AppliedJobIterationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppliedJobIteration> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AppliedJobIteration>) => response.ok),
                map((appliedJobIteration: HttpResponse<AppliedJobIteration>) => appliedJobIteration.body)
            );
        }
        return of(new AppliedJobIteration());
    }
}

export const appliedJobIterationRoute: Routes = [
    {
        path: '',
        component: AppliedJobIterationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.appliedJobIteration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AppliedJobIterationDetailComponent,
        resolve: {
            appliedJobIteration: AppliedJobIterationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJobIteration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AppliedJobIterationUpdateComponent,
        resolve: {
            appliedJobIteration: AppliedJobIterationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJobIteration.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AppliedJobIterationUpdateComponent,
        resolve: {
            appliedJobIteration: AppliedJobIterationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJobIteration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appliedJobIterationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AppliedJobIterationDeletePopupComponent,
        resolve: {
            appliedJobIteration: AppliedJobIterationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJobIteration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
