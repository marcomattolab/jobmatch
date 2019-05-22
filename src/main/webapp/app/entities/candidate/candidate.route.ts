import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { Candidate, ICandidate } from 'app/shared/model/candidate.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CandidateDeletePopupComponent } from './candidate-delete-dialog.component';
import { CandidateProfileListComponent } from './candidate-profile-list.component';
import { CandidateProfileComponent } from './candidate-profile.component';
import { CandidateUpdateComponent } from './candidate-update.component';
import { CandidateService } from './candidate.service';

@Injectable( { providedIn: 'root' } )
export class CandidateResolve implements Resolve<ICandidate> {
    constructor( private service: CandidateService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<ICandidate> {
        const id = route.params['id'] ? route.params['id'] : null;
        if ( id ) {
            return this.service.find( id ).pipe(
                filter(( response: HttpResponse<Candidate> ) => response.ok ),
                map(( candidate: HttpResponse<Candidate> ) => candidate.body )
            );
        }
        return of( new Candidate() );
    }
}

@Injectable( { providedIn: 'root' } )
export class CandidateByUserResolve implements Resolve<ICandidate> {
    constructor( private service: CandidateService ) { }

    resolve( route: ActivatedRouteSnapshot, state: RouterStateSnapshot ): Observable<ICandidate> {
        return this.service.findByUser().pipe(
            filter(( response: HttpResponse<Candidate> ) => response.ok ),
            map(( candidate: HttpResponse<Candidate> ) => candidate.body )
        );
    }
}

export const candidateRoute: Routes = [
    {
        path: 'select',
        component: CandidateProfileListComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.candidate.home.title',
            selectMode: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: '',
        component: CandidateProfileListComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.candidate.home.title',
            selectMode: false
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'current',
        component: CandidateProfileComponent,
        resolve: {
            candidate: CandidateByUserResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_CANDIDATE],
            pageTitle: 'jobmatchApp.candidate.home.title',
            showBackBtn: false
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id',
        component: CandidateProfileComponent,
        resolve: {
            candidate: CandidateResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_COMPANY, AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION],
            pageTitle: 'jobmatchApp.candidate.home.title',
            showBackBtn: true
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CandidateUpdateComponent,
        resolve: {
            candidate: CandidateResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.candidate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    //    {
    //        path: ':id/edit',
    //        component: CandidateUpdateComponent,
    //        resolve: {
    //            candidate: CandidateResolve
    //        },
    //        data: {
    //            authorities: [AuthoritiesConstants.ROLE_ADMIN],
    //            pageTitle: 'jobmatchApp.candidate.home.title'
    //        },
    //        canActivate: [UserRouteAccessService]
    //    }
    //    ,
    //    {
    //        path: 'editCurrentCandidate',
    //        component: CandidateUpdateComponent,
    //        resolve: {
    //            candidate: CandidateUserResolve
    //        },
    //        data: {
    //            authorities: ['ROLE_CANDIDATE'],
    //            pageTitle: 'jobmatchApp.candidate.home.title'
    //        },
    //        canActivate: [UserRouteAccessService]
    //    }
];

export const candidatePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CandidateDeletePopupComponent,
        resolve: {
            candidate: CandidateResolve
        },
        data: {
            authorities: [AuthoritiesConstants.ROLE_ADMIN],
            pageTitle: 'jobmatchApp.candidate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
