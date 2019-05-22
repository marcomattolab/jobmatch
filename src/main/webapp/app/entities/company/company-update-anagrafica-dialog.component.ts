import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal, NgbTypeahead, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { CompanySectorService } from 'app/entities/company-sector';
import { Country } from 'app/shared/model/candidate.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { ICompany } from 'app/shared/model/company.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map, tap } from 'rxjs/operators';
import { CompanyService } from './company.service';

@Component( {
    selector: 'jhi-company-update-anagrafica-dialog',
    templateUrl: './company-update-anagrafica-dialog.component.html'
} )
export class CompanyUpdateAnagraficaDialogComponent implements OnInit {
    company: ICompany;
    isSaving = false;
    countries = Object.keys( Country );
    companysectors: ICompanySector[];

    @ViewChild( 'companySectorTypeahead' )
    companySectorTypeahead: NgbTypeahead;
    companySectorFocus$ = new Subject<string>();
    companySectorClick$ = new Subject<string>();
    companySectorFieldTouched = false;

    constructor(
        protected companyService: CompanyService,
        protected companySectorService: CompanySectorService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService
    ) { }

    ngOnInit() {
        this.loadCompanySectors();
    }

    clear() {
        this.activeModal.dismiss( 'cancel' );
    }

    confirmUpdate() {
        this.isSaving = true;
        if ( this.company.id ) {
            this.clearNullInfo();
            this.subscribeToSaveResponse( this.companyService.update( this.company ) );
        } else {
            this.isSaving = false;
        }
    }

    private clearNullInfo() {
        if ( !this.company.phone ) {
            this.company.phone = null;
        }
        if ( !this.company.mobilePhone ) {
            this.company.mobilePhone = null;
        }
        if ( !this.company.urlSite ) {
            this.company.urlSite = null;
        }
        if ( !this.company.vatNumber ) {
            this.company.vatNumber = null;
        }
    }

    private subscribeToSaveResponse( result: Observable<HttpResponse<ICompany>> ) {
        result.subscribe(( res: HttpResponse<ICompany> ) => this.onSaveSuccess(), () => this.isSaving = false );
    }

    private onSaveSuccess() {
        this.eventManager.broadcast( {
            name: 'companyModified',
            content: 'company s anagrafica has been modified'
        } );
        this.activeModal.dismiss( true );
    }

    loadCompanySectors() {
        this.companySectorService.findAll().subscribe(
            ( res: HttpResponse<ICompanySector[]> ) => {
                this.companysectors = res.body;
                if ( this.company.sectorId ) {
                    const sector = this.companysectors.find( cs => cs.id === this.company.sectorId );
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
                    this.company.sectorId = null;
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
        this.company.sectorId = sectorFound.id;
        this.companySectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }
}

/*
@Component( {
    selector: 'jhi-company-update-anagrafica-popup',
    template: ''
} )
export class CompanyUpdateAnagraficaPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor( protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { company } ) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open( CompanyUpdateAnagraficaDialogComponent as Component, { size: 'lg', backdrop: 'static' } );
                this.ngbModalRef.componentInstance.company = company;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate( ['/company', { outlets: { popup: null } }] );
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate( ['/company', { outlets: { popup: null } }] );
                        this.ngbModalRef = null;
                    }
                );
            }, 0 );
        } );
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}*/
