import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';
import { SponsoringInstitutionService } from './sponsoring-institution.service';
import { SponsoringInstitutionComponent } from './sponsoring-institution.component';
import { SponsoringInstitutionDetailComponent } from './sponsoring-institution-detail.component';
import { SponsoringInstitutionUpdateComponent } from './sponsoring-institution-update.component';
import { SponsoringInstitutionDeletePopupComponent } from './sponsoring-institution-delete-dialog.component';
import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { SponsoringInstitutionProfileComponent } from '.';

@Injectable( { providedIn: 'root' } )
export class SponsoringInstitutionResolve implements Resolve<ISponsoringInstitution> {
    constructor( private service: SponsoringInstitutionService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<ISponsoringInstitution> {
        const id = route.params['id'] ? route.params['id'] : null;
        if ( id ) {
            return this.service.find( id ).pipe(
                filter(( response: HttpResponse<SponsoringInstitution> ) => response.ok ),
                map(( sponsoringInstitution: HttpResponse<SponsoringInstitution> ) => sponsoringInstitution.body )
            );
        }
        return of( new SponsoringInstitution() );
    }
}

@Injectable( { providedIn: 'root' } )
export class SponsoringInstitutionByUserResolve implements Resolve<ISponsoringInstitution> {
    constructor( private service: SponsoringInstitutionService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<ISponsoringInstitution> {
        return this.service.findByUser().pipe(
            filter(( response: HttpResponse<SponsoringInstitution> ) => response.ok ),
            map(( sponsoringInstitution: HttpResponse<SponsoringInstitution> ) => sponsoringInstitution.body )
        );
    }
}

export const sponsoringInstitutionRoute: Routes = [
    {
        path: '',
        component: SponsoringInstitutionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities:  AuthoritiesConstants.ALL_ROLES,
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SponsoringInstitutionDetailComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionResolve
        },
        data: {
            authorities: AuthoritiesConstants.ALL_ROLES,
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'current',
        component: SponsoringInstitutionProfileComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionByUserResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title',
            showBackBtn: false
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id',
        component: SponsoringInstitutionProfileComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionResolve
        },
        data: {
            authorities: AuthoritiesConstants.ALL_ROLES,
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title',
            showBackBtn: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'viewCurrentSponsoringInstitution',
        component: SponsoringInstitutionDetailComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionByUserResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SponsoringInstitutionUpdateComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SponsoringInstitutionUpdateComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sponsoringInstitutionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SponsoringInstitutionDeletePopupComponent,
        resolve: {
            sponsoringInstitution: SponsoringInstitutionResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.sponsoringInstitution.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
