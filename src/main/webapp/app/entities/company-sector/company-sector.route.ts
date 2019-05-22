import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { CompanySector, ICompanySector } from 'app/shared/model/company-sector.model';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompanySectorDeletePopupComponent } from './company-sector-delete-dialog.component';
import { CompanySectorDetailComponent } from './company-sector-detail.component';
import { CompanySectorUpdateComponent } from './company-sector-update.component';
import { CompanySectorComponent } from './company-sector.component';
import { CompanySectorService } from './company-sector.service';

@Injectable({ providedIn: 'root' })
export class CompanySectorResolve implements Resolve<ICompanySector> {
    constructor(private service: CompanySectorService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompanySector> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CompanySector>) => response.ok),
                map((companySector: HttpResponse<CompanySector>) => companySector.body)
            );
        }
        return of(new CompanySector());
    }
}

@Injectable({ providedIn: 'root' })
export class CompanySectorFindAllResolve implements Resolve<ICompanySector[]> {
    constructor(private service: CompanySectorService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompanySector[]> {
        return this.service.findAll().pipe(
            filter((response: HttpResponse<ICompanySector[]>) => response.ok),
            map((companySector: HttpResponse<ICompanySector[]>) => companySector.body)
        );
    }
}

export const companySectorRoute: Routes = [
    {
        path: '',
        component: CompanySectorComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jobmatchApp.companySector.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CompanySectorDetailComponent,
        resolve: {
            companySector: CompanySectorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySector.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CompanySectorUpdateComponent,
        resolve: {
            companySector: CompanySectorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySector.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CompanySectorUpdateComponent,
        resolve: {
            companySector: CompanySectorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySector.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companySectorPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CompanySectorDeletePopupComponent,
        resolve: {
            companySector: CompanySectorResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jobmatchApp.companySector.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
