import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JobExperience } from 'app/shared/model/job-experience.model';
import { JobExperienceService } from './job-experience.service';
import { JobExperienceComponent } from './job-experience.component';
import { JobExperienceDetailComponent } from './job-experience-detail.component';
import { JobExperienceUpdateComponent } from './job-experience-update.component';
import { JobExperienceDeletePopupComponent } from './job-experience-delete-dialog.component';
import { IJobExperience } from 'app/shared/model/job-experience.model';

@Injectable({ providedIn: 'root' })
export class JobExperienceResolve implements Resolve<IJobExperience> {
    constructor(private service: JobExperienceService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobExperience> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JobExperience>) => response.ok),
                map((jobExperience: HttpResponse<JobExperience>) => jobExperience.body)
            );
        }
        return of(new JobExperience());
    }
}

@Injectable({ providedIn: 'root' })
export class JobExperiencesByCandidateResolve implements Resolve<IJobExperience[]> {
    constructor(private service: JobExperienceService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobExperience[]> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.findAll(id).pipe(
                filter((response: HttpResponse<JobExperience[]>) => response.ok),
                map((jobExperience: HttpResponse<JobExperience[]>) => jobExperience.body)
            );
        }
        return of([]);
    }
}

@Injectable({ providedIn: 'root' })
export class JobExperienceByCandidateResolve implements Resolve<IJobExperience[]> {
    constructor(private service: JobExperienceService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobExperience[]> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.query({ 'candidatoId.equals': id }).pipe(
                filter((response: HttpResponse<JobExperience[]>) => response.ok),
                map((jobExperience: HttpResponse<JobExperience[]>) => jobExperience.body)
            );
        }
        return of([]);
    }
}

@Injectable({ providedIn: 'root' })
export class JobExperienceProfileResolve implements Resolve<IJobExperience> {
    constructor(private service: JobExperienceService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IJobExperience> {
        const id = route.params['id-job-experience'] ? route.params['id-job-experience'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<JobExperience>) => response.ok),
                map((jobExperience: HttpResponse<JobExperience>) => jobExperience.body)
            );
        }
        return of(new JobExperience());
    }
}

export const jobExperienceRoute: Routes = [
    {
        path: '',
        component: JobExperienceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.jobExperience.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: JobExperienceDetailComponent,
        resolve: {
            jobExperience: JobExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobExperience.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: JobExperienceUpdateComponent,
        resolve: {
            jobExperience: JobExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobExperience.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: JobExperienceUpdateComponent,
        resolve: {
            jobExperience: JobExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobExperience.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobExperiencePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: JobExperienceDeletePopupComponent,
        resolve: {
            jobExperience: JobExperienceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.jobExperience.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
