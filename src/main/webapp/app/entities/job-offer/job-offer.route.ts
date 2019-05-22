import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { AccountService, UserRouteAccessService } from 'app/core';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { IJobOffer, JobOffer } from 'app/shared/model/job-offer.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobOfferDeletePopupComponent } from './job-offer-delete-dialog.component';
import { JobOfferDetailComponent } from './job-offer-detail.component';
import { JobOfferUpdateComponent } from './job-offer-update.component';
import { JobOfferComponent } from './job-offer.component';
import { JobOfferService } from './job-offer.service';
import { JobsOfferListComponent } from './jobs-offer-list.component';
import { JobsOfferPromoteComponent } from './jobs-offer-promote.component';

@Injectable({ providedIn: 'root' })
export class JobOfferResolve implements Resolve<IJobOffer> {
    constructor(private service: JobOfferService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobOffer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JobOffer>) => response.ok),
                map((jobOffer: HttpResponse<JobOffer>) => jobOffer.body)
            );
        }
        return of(new JobOffer());
    }
}

@Injectable({ providedIn: 'root' })
export class JobOfferProfileResolve implements Resolve<IJobOffer> {
    constructor(private service: JobOfferService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobOffer> {
        const id = route.params['id-job-offer'] ? route.params['id-job-offer'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<IJobOffer>) => response.ok),
                map((jobOffer: HttpResponse<IJobOffer>) => jobOffer.body)
            );
        }
        return of(new JobOffer());
    }
}

@Injectable({ providedIn: 'root' })
export class JobsOfferListResolve implements Resolve<IJobOffer[]> {
    constructor(private service: JobOfferService, private accountService: AccountService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobOffer[]> {
        return this.service.query().pipe(
            filter((response: HttpResponse<IJobOffer[]>) => response.ok),
            map((jobOffer: HttpResponse<IJobOffer[]>) => jobOffer.body)
        );
    }
}

export const jobOfferRoute: Routes = [
    {
        path: '',
        component: JobOfferComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JobOfferDetailComponent,
        resolve: {
            jobOffer: JobOfferResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY,
            AuthoritiesConstants.ROLE_CANDIDATE, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'list',
        component: JobsOfferListComponent,
        data: {
            authorities: [AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'promote',
        component: JobsOfferPromoteComponent,
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'search',
        component: JobsOfferListComponent,
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY,
            AuthoritiesConstants.ROLE_CANDIDATE, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            searchMode: true,
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JobOfferUpdateComponent,
        resolve: {
            jobOffer: JobOfferResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JobOfferUpdateComponent,
        resolve: {
            jobOffer: JobOfferResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobOfferPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JobOfferDeletePopupComponent,
        resolve: {
            jobOffer: JobOfferResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.jobOffer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
