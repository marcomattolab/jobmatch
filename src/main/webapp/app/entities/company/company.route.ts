import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { Company, ICompany } from 'app/shared/model/company.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanyUpdateJobOfferComponent } from '.';
import { CompanySectorFindAllResolve } from '../company-sector/company-sector.route';
import { JobOfferProfileResolve } from '../job-offer/job-offer.route';
import { CompanyDeletePopupComponent } from './company-delete-dialog.component';
import { CompanyDetailComponent } from './company-detail.component';
// import { CompanyComponent } from './company.component';
import { CompanyProfileListComponent } from './company-profile-list.component';
import { CompanyProfileComponent } from './company-profile.component';
import { CompanyUpdateComponent } from './company-update.component';
import { CompanyService } from './company.service';

@Injectable( { providedIn: 'root' } )
export class CompanyResolve implements Resolve<ICompany> {
    constructor( private service: CompanyService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<ICompany> {
        const id = route.params['id'] ? route.params['id'] : null;
        if ( id ) {
            return this.service.find( id ).pipe(
                filter(( response: HttpResponse<Company> ) => response.ok ),
                map(( company: HttpResponse<Company> ) => company.body )
            );
        }
        return of( new Company() );
    }
}

@Injectable( { providedIn: 'root' } )
export class CompanyByUserResolve implements Resolve<ICompany> {
    constructor( private service: CompanyService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<ICompany> {
        return this.service.findByUser().pipe(
            filter(( response: HttpResponse<Company> ) => response.ok ),
            map(( company: HttpResponse<Company> ) => company.body )
        );
    }
}

export const companyRoute: Routes = [
    {
        path: '',
        component: CompanyProfileListComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            sectors: CompanySectorFindAllResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CANDIDATE, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    //    {
    //        path: ':id/view',
    //        component: CompanyDetailComponent,
    //        resolve: {
    //            company: CompanyResolve
    //        },
    //        data: {
    //            authorities: [AuthoritiesConstants.ROLE_ADMIN],
    //            pageTitle: 'jobmatchApp.company.home.title'
    //        },
    //        canActivate: [UserRouteAccessService]
    //    },
    {
        path: 'current',
        component: CompanyProfileComponent,
        resolve: {
            company: CompanyByUserResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_COMPANY],
            pageTitle: 'jobmatchApp.company.home.title',
            showBackBtn: false
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id',
        component: CompanyProfileComponent,
        resolve: {
            company: CompanyResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CANDIDATE, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.company.home.title',
            showBackBtn: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new/job-offer',
        component: CompanyUpdateJobOfferComponent,
        resolve: {
            jobOffer: JobOfferProfileResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'edit/job-offer/:id-job-offer',
        component: CompanyUpdateJobOfferComponent,
        resolve: {
            jobOffer: JobOfferProfileResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'viewCurrentCompany',
        component: CompanyDetailComponent,
        resolve: {
            company: CompanyByUserResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CompanyUpdateComponent,
        resolve: {
            company: CompanyResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CompanyUpdateComponent,
        resolve: {
            company: CompanyResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CompanyDeletePopupComponent,
        resolve: {
            company: CompanyResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.company.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
