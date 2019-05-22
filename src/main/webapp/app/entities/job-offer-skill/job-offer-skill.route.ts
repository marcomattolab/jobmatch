import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { JobOfferSkillService } from './job-offer-skill.service';
import { JobOfferSkillComponent } from './job-offer-skill.component';
import { JobOfferSkillDetailComponent } from './job-offer-skill-detail.component';
import { JobOfferSkillUpdateComponent } from './job-offer-skill-update.component';
import { JobOfferSkillDeletePopupComponent } from './job-offer-skill-delete-dialog.component';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';

@Injectable({ providedIn: 'root' })
export class JobOfferSkillResolve implements Resolve<IJobOfferSkill> {
    constructor(private service: JobOfferSkillService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobOfferSkill> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JobOfferSkill>) => response.ok),
                map((jobOfferSkill: HttpResponse<JobOfferSkill>) => jobOfferSkill.body)
            );
        }
        return of(new JobOfferSkill());
    }
}

export const jobOfferSkillRoute: Routes = [
    {
        path: '',
        component: JobOfferSkillComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.jobOfferSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JobOfferSkillDetailComponent,
        resolve: {
            jobOfferSkill: JobOfferSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobOfferSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JobOfferSkillUpdateComponent,
        resolve: {
            jobOfferSkill: JobOfferSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobOfferSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JobOfferSkillUpdateComponent,
        resolve: {
            jobOfferSkill: JobOfferSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobOfferSkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobOfferSkillPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JobOfferSkillDeletePopupComponent,
        resolve: {
            jobOfferSkill: JobOfferSkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobOfferSkill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
