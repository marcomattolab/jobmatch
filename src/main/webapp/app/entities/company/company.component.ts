import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ICompany } from 'app/shared/model/company.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CompanyService } from './company.service';

import { FileService } from 'app/shared/file/file.service';

@Component( {
    selector: 'jhi-company',
    templateUrl: './company.component.html'
} )
export class CompanyComponent implements OnInit, OnDestroy {
    currentAccount: any;
    companies: ICompany[];
    companySectors: ICompanySector[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        protected companyService: CompanyService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected fileService: FileService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe( data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            console.log('sectors', data.sectors);
            this.companySectors = data.sectors;
        } );
    }

    loadAll() {
        this.companyService
            .query( {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            } )
            .subscribe(
            ( res: HttpResponse<ICompany[]> ) => this.paginateCompanies( res.body, res.headers ),
            ( res: HttpErrorResponse ) => this.onError( res.message )
            );
    }

    loadPage( page: number ) {
        if ( page !== this.previousPage ) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate( ['/company'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )
            }
        } );
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate( [
            '/company',
            {
                page: this.page,
                sort: this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )
            }
        ] );
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then( account => {
            this.currentAccount = account;
        } );
        this.registerChangeInCompanies();
    }

    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
    }

    trackId( index: number, item: ICompany ) {
        return item.id;
    }

    byteSize( field ) {
        return this.dataUtils.byteSize( field );
    }

    openFile( contentType, field ) {
        return this.dataUtils.openFile( contentType, field );
    }

    registerChangeInCompanies() {
        this.eventSubscriber = this.eventManager.subscribe( 'companyListModification', response => this.loadAll() );
    }

    sort() {
        const result = [this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )];
        if ( this.predicate !== 'id' ) {
            result.push( 'id' );
        }
        return result;
    }

    protected paginateCompanies( data: ICompany[], headers: HttpHeaders ) {
        this.links = this.parseLinks.parse( headers.get( 'link' ) );
        this.totalItems = parseInt( headers.get( 'X-Total-Count' ), 10 );
        this.companies = data;
        this.companies.forEach(
            comp => {
                const sector = this.companySectors.find( sec => sec.id === comp.sectorId );
                if ( sector ) {
                    comp.sectorDescription = sector.description;
                }
            }
        );

    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
    }

    companyImageUrl( imageUrl: string ): string {
        return this.fileService.getDownloadFileUrl( imageUrl );
    }
}
