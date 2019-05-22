import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SkillTag } from 'app/shared/model/skill-tag.model';
import { SkillTagService } from './skill-tag.service';
import { SkillTagComponent } from './skill-tag.component';
import { SkillTagDetailComponent } from './skill-tag-detail.component';
import { SkillTagUpdateComponent } from './skill-tag-update.component';
import { SkillTagDeletePopupComponent } from './skill-tag-delete-dialog.component';
import { ISkillTag } from 'app/shared/model/skill-tag.model';

@Injectable({ providedIn: 'root' })
export class SkillTagResolve implements Resolve<ISkillTag> {
    constructor(private service: SkillTagService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkillTag> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SkillTag>) => response.ok),
                map((skillTag: HttpResponse<SkillTag>) => skillTag.body)
            );
        }
        return of(new SkillTag());
    }
}

export const skillTagRoute: Routes = [
    {
        path: '',
        component: SkillTagComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.skillTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SkillTagDetailComponent,
        resolve: {
            skillTag: SkillTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.skillTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SkillTagUpdateComponent,
        resolve: {
            skillTag: SkillTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.skillTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SkillTagUpdateComponent,
        resolve: {
            skillTag: SkillTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.skillTag.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const skillTagPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SkillTagDeletePopupComponent,
        resolve: {
            skillTag: SkillTagResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.skillTag.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
