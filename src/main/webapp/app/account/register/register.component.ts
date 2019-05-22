import { Component, OnInit, AfterViewInit, Renderer, ElementRef, ViewChild } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef, NgbTypeahead, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService, JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { LoginModalService } from 'app/core';
import { Register } from './register.service';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { Country } from 'app/shared/model/candidate.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanySectorService } from 'app/entities/company-sector';
import { debounceTime, distinctUntilChanged, filter, map, tap } from 'rxjs/operators';
import { Observable, Subscription, Subject, merge } from 'rxjs';

@Component( {
    selector: 'jhi-register',
    templateUrl: './register.component.html'
} )
export class RegisterComponent implements OnInit, AfterViewInit {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    roleAccount: string;
    companysectors: ICompanySector[];
    countries = Object.keys( Country );
    foundationDateDp: any;

    @ViewChild( 'companySectorTypeahead' )
    companySectorTypeahead: NgbTypeahead;
    companySectorFocus$ = new Subject<string>();
    companySectorClick$ = new Subject<string>();
    companySectorFieldTouched = false;

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private activatedRoute: ActivatedRoute,
        private companySectorService: CompanySectorService,
        private jhiAlertService: JhiAlertService
    ) { }

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        this.activatedRoute.data.subscribe( data => {
            this.roleAccount = data.roleAccount;
            if ( !this.hasRoleCandidate() ) {
                this.companySectorService
                    .findAll()
                    .pipe(
                    filter(( mayBeOk: HttpResponse<ICompanySector[]> ) => mayBeOk.ok ),
                    map(( response: HttpResponse<ICompanySector[]> ) => response.body )
                    )
                    .subscribe(( res: ICompanySector[] ) => ( this.companysectors = res ), ( res: HttpErrorResponse ) => this.onError( res.message ) );
            }
        } );
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod( this.elementRef.nativeElement.querySelector( '#login' ), 'focus', [] );
    }

    register() {
        if ( this.registerAccount.password !== this.confirmPassword ) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then( key => {
                this.registerAccount.langKey = key;
                this.registerAccount.roleAccount = this.roleAccount;
                this.registerService.save( this.registerAccount ).subscribe(
                    () => {
                        this.success = true;
                    },
                    response => this.processError( response )
                );
            } );
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError( response: HttpErrorResponse ) {
        this.success = null;
        if ( response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE ) {
            this.errorUserExists = 'ERROR';
        } else if ( response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE ) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    onSelectCompanySize( event: any ) {
        this.registerAccount.numberOfEmployee = null;
    }

    hasRoleCandidate() {
        return this.roleAccount === AuthoritiesConstants.ROLE_CANDIDATE;
    }

    hasRoleCompany() {
        return this.roleAccount === AuthoritiesConstants.ROLE_COMPANY;
    }

    hasRoleSponsoringInstitution() {
        return this.roleAccount === AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION;
    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
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
                    this.registerAccount.companySectorId = null;
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
        this.registerAccount.companySectorId = sectorFound.id;
        this.companySectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }
}
