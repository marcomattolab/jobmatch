import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Education } from 'app/shared/model/education.model';
import { EducationService } from './education.service';
import { EducationComponent } from './education.component';
import { EducationDetailComponent } from './education-detail.component';
import { EducationUpdateComponent } from './education-update.component';
import { EducationDeletePopupComponent } from './education-delete-dialog.component';
import { IEducation } from 'app/shared/model/education.model';

@Injectable({ providedIn: 'root' })
export class EducationResolve implements Resolve<IEducation> {
    constructor(private service: EducationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEducation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Education>) => response.ok),
                map((education: HttpResponse<Education>) => education.body)
            );
        }
        return of(new Education());
    }
}

@Injectable({ providedIn: 'root' })
export class EducationsByCandidateResolve implements Resolve<IEducation[]> {
    constructor(private service: EducationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEducation[]> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.findAll(id).pipe(
                filter((response: HttpResponse<Education[]>) => response.ok),
                map((education: HttpResponse<Education[]>) => education.body)
            );
        }
        return of([]);
    }
}

@Injectable({ providedIn: 'root' })
export class EducationProfileResolve implements Resolve<IEducation> {
    constructor(private service: EducationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEducation> {
        const id = route.params['id-education'] ? route.params['id-education'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Education>) => response.ok),
                map((education: HttpResponse<Education>) => education.body)
            );
        }
        return of(new Education());
    }
}

export const educationRoute: Routes = [
    {
        path: '',
        component: EducationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.education.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EducationDetailComponent,
        resolve: {
            education: EducationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.education.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EducationUpdateComponent,
        resolve: {
            education: EducationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.education.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EducationUpdateComponent,
        resolve: {
            education: EducationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.education.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const educationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EducationDeletePopupComponent,
        resolve: {
            education: EducationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.education.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
