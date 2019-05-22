import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CompanySkill, ICompanySkill } from 'app/shared/model/company-skill.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanySkillDeletePopupComponent } from './company-skill-delete-dialog.component';
import { CompanySkillDetailComponent } from './company-skill-detail.component';
import { CompanySkillUpdateComponent } from './company-skill-update.component';
import { CompanySkillComponent } from './company-skill.component';
import { CompanySkillService } from './company-skill.service';

@Injectable({ providedIn: 'root' })
export class CompanySkillResolve implements Resolve<ICompanySkill> {
    constructor(private service: CompanySkillService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompanySkill> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CompanySkill>) => response.ok),
                map((companySkill: HttpResponse<CompanySkill>) => companySkill.body)
            );
        }
        return of(new CompanySkill());
    }
}

@Injectable({ providedIn: 'root' })
export class CompanySkillProfileResolve implements Resolve<ICompanySkill> {
    constructor(private service: CompanySkillService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompanySkill> {
        const id = route.params['id-skill'] ? route.params['id-skill'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ICompanySkill>) => response.ok),
                map((education: HttpResponse<ICompanySkill>) => education.body)
            );
        }
        return of(new CompanySkill());
    }
}

export const companySkillRoute: Routes = [
    {
        path: '',
        component: CompanySkillComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.companySkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CompanySkillDetailComponent,
        resolve: {
            companySkill: CompanySkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CompanySkillUpdateComponent,
        resolve: {
            companySkill: CompanySkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CompanySkillUpdateComponent,
        resolve: {
            companySkill: CompanySkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySkill.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companySkillPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CompanySkillDeletePopupComponent,
        resolve: {
            companySkill: CompanySkillResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySkill.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
