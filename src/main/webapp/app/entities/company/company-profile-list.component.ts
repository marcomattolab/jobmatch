import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { NgForm, FormControl } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbTypeahead, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { merge, Observable, Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map, tap } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { Country } from 'app/shared/model/candidate.model';
import { ICompany, CompanySearchFilter } from 'app/shared/model/company.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE, enableFooter, disableFooter } from 'app/shared';
import { CompanyService } from './company.service';
import { FileService } from 'app/shared/file/file.service';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { CompanySectorService } from 'app/entities/company-sector';

@Component( {
    selector: 'jhi-company-profile-list',
    templateUrl: './company-profile-list.component.html'
} )
export class CompanyProfileListComponent implements OnInit, OnDestroy, AfterViewInit {
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
    showTable = false;

    // form ricerca
    filters: CompanySearchFilter = {};
    @ViewChild( 'searchForm' )
    searchForm: NgForm;

    // select nazioni
    countries = Object.keys( Country );

    // suggestion settori
    companysectors: ICompanySector[];
    @ViewChild( 'companySectorTypeahead' )
    companySectorTypeahead: NgbTypeahead;
    companySectorFocus$ = new Subject<string>();
    companySectorClick$ = new Subject<string>();
    companySectorFieldTouched = false;

    constructor(
        protected companyService: CompanyService,
        protected companySectorService: CompanySectorService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected searchFilterParamsService: SearchFilterParamsService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe( data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
            this.companySectors = data.sectors;
        } );
    }

    search() {
        const queryParams: any = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        if ( this.filters ) {
            if ( this.filters.companyName ) {
                queryParams['companyName.contains'] = this.filters.companyName;
            }
            if ( this.filters.city ) {
                queryParams['city.contains'] = this.filters.city;
            }
            if ( this.filters.country ) {
                queryParams['country.equals'] = this.filters.country;
            }
            if ( this.filters.sectorId ) {
                queryParams['sectorId.equals'] = this.filters.sectorId;
            }
        }
        this.searchFilterParamsService.storePathFormParams( this.router.url, this.searchForm.form );

        this.companyService
            .query( queryParams )
            .subscribe(
            ( res: HttpResponse<ICompany[]> ) => this.paginateCompanies( res.body, res.headers ),
            ( res: HttpErrorResponse ) => this.onError( res.message )
            );
    }

    loadAll() {
        this.search();
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
        this.accountService.identity().then( account => {
            this.currentAccount = account;
        } );
        this.registerChangeInCompanies();
        enableFooter(document);
    }

    ngAfterViewInit() {
        setTimeout(() => {
            this.initFilters();
            this.loadCompanySectors();
            this.loadAll();
        }, 0 );
    }

    initFilters() {
        this.filters = new CompanySearchFilter();
        this.searchFilterParamsService.patchFormWithParams( this.router.url, this.searchForm.form );
    }

    resetFilters() {
        this.filters = new CompanySearchFilter();
        this.searchFilterParamsService.clearPathFormParams( this.router.url );
    }

    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
        disableFooter(document);
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

    loadCompanySectors() {
        this.companySectorService.findAll().subscribe(
            ( res: HttpResponse<ICompanySector[]> ) => {
                this.companysectors = res.body;
                if ( this.searchForm && this.searchForm.controls.companySector ) {
                    const sectorId = +this.searchForm.controls.companySector;
                    const sector = this.companysectors.find( cs => cs.id === sectorId );
                    if ( sector ) {
                        this.setCompanySector( sector );
                    }
                }
            },
            ( res: HttpErrorResponse ) => this.jhiAlertService.error( res.message, null, null )
        );
    }

    searchCompanySector = ( text$: Observable<string> ) => {
        const debouncedText$ = text$.pipe( debounceTime( 200 ), distinctUntilChanged() );

        const clicksWithClosedPopup$ = this.companySectorClick$.pipe(
            filter(() => !this.companySectorTypeahead.isPopupOpen() ),
            tap(() => this.companySectorFieldTouched = true ) );

        const inputFocus$ = this.companySectorFocus$.pipe( tap(() => this.companySectorFieldTouched = true ) );

        return merge( debouncedText$, inputFocus$, clicksWithClosedPopup$ ).pipe(
            map( term => {
                term = term.trim();

                const companyySectorFound = this.companysectors.find( sector =>
                    this.getFormattedCompanySector( sector ) === term );

                if ( !companyySectorFound ) {
                    this.filters.sectorId = null;
                }

                return ( term === '' ?
                    this.getAvailableSectors() :
                    this.getAvailableSectors()
                        .filter( v => v.toLowerCase().indexOf( term.toLowerCase() ) > -1 )
                );
            }
            )
        );
    };

    private getAvailableSectors(): Array<string> {
        return this.companysectors.map( sector => this.getFormattedCompanySector( sector ) );
    }

    private getFormattedCompanySector( sector: ICompanySector | any ): string {
        return `${sector.description}`;
    }

    onSelectCompanySector( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        const sectorFound = this.companysectors.find( companySector =>
            this.getFormattedCompanySector( companySector ) === ngbTypeaheadSelectItem.item );
        if ( sectorFound ) {
            this.setCompanySector( sectorFound );
        }
    }

    private setCompanySector( sectorFound ) {
        this.filters.sectorId = sectorFound.id;
        //        this.searchForm.form.addControl( 'sectorId', new FormControl( this.filters.sectorId ) );
        //        this.searchForm.form.updateValueAndValidity();
        this.companySectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }

}
