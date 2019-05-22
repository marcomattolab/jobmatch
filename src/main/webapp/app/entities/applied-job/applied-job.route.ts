import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { AppliedJob, IAppliedJob, AppliedJobItem } from 'app/shared/model/applied-job.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobOfferProfileResolve } from '../job-offer/job-offer.route';
import { AppliedJobDeletePopupComponent } from './applied-job-delete-dialog.component';
import { AppliedJobDetailComponent } from './applied-job-detail.component';
import { AppliedJobUpdateComponent } from './applied-job-update.component';
import { AppliedJobComponent } from './applied-job.component';
import { AppliedJobService } from './applied-job.service';

@Injectable( { providedIn: 'root' } )
export class AppliedJobResolve implements Resolve<IAppliedJob> {
    constructor( private service: AppliedJobService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<IAppliedJob> {
        const id = route.params['id'] ? route.params['id'] : null;
        if ( id ) {
            return this.service.find( id ).pipe(
                filter(( response: HttpResponse<AppliedJob> ) => response.ok ),
                map(( appliedJob: HttpResponse<AppliedJob> ) => appliedJob.body )
            );
        }
        return of( new AppliedJob() );
    }
}

@Injectable( { providedIn: 'root' } )
export class AppliedJobsItemByCompanyResolve implements Resolve<AppliedJobItem[]> {
    constructor( private service: AppliedJobService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<AppliedJobItem[]> {
        const id = route.params['id-job-offer'] ? route.params['id-job-offer'] : null;
        if ( id ) {
            return this.service.queryItems( { 'jobOfferId.equals': id } ).pipe(
                filter(( response: HttpResponse<AppliedJobItem[]> ) => response.ok ),
                map(( appliedJob: HttpResponse<AppliedJobItem[]> ) => appliedJob.body )
            );
        }
        return of( [] );
    }
}

export const appliedJobRoute: Routes = [
    {
        path: '',
        component: AppliedJobComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_CANDIDATE, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.appliedJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'jobOffer/:id-job-offer',
        component: AppliedJobComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.appliedJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AppliedJobDetailComponent,
        resolve: {
            appliedJob: AppliedJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AppliedJobUpdateComponent,
        resolve: {
            appliedJob: AppliedJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AppliedJobUpdateComponent,
        resolve: {
            appliedJob: AppliedJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJob.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appliedJobPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AppliedJobDeletePopupComponent,
        resolve: {
            appliedJob: AppliedJobResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.appliedJob.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
