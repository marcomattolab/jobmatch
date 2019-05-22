import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiLanguageHelper, User, UserService, RegistrationCandidateUser, RegistrationCompanyUser, RegistrationSponsoringInstitutionUser } from 'app/core';
import { Register } from 'app/account/register/register.service';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { CandidateService } from 'app/entities/candidate';
import { CompanyService } from 'app/entities/company';
import { SponsoringInstitutionService } from 'app/entities/sponsoring-institution';
import { pipeToResponse } from 'app/shared/util/request-util';
import { isNullOrUndefined } from 'util';
import { Subject } from 'rxjs/internal/Subject';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { map } from 'rxjs/internal/operators/map';
import { filter } from 'rxjs/internal/operators/filter';
import { tap } from 'rxjs/internal/operators/tap';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';
import { distinctUntilChanged } from 'rxjs/internal/operators/distinctUntilChanged';
import { Observable } from 'rxjs/internal/Observable';
import { merge } from 'rxjs';
import { NgbTypeahead, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { Country } from 'app/shared/model/candidate.model';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { HttpErrorResponse } from '@angular/common/http/src/response';
import { LOGIN_ALREADY_USED_TYPE, EMAIL_ALREADY_USED_TYPE } from 'app/shared/constants/error.constants';

@Component( {
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
} )
export class UserMgmtUpdateComponent implements OnInit {
    user: User;
    userRoleDetail: any;
    languages: any[];
    authorities: any[];
    companysectors: ICompanySector[];
    currentAuthority: string;
    currentSectorDescription: string;
    countries = Object.keys( Country );
    isSaving: boolean;
    confirmPassword: string;
    passwNotMatch: boolean;
    errorEmailExists: boolean;
    errorUserExists: boolean;
    createMode: boolean;

    @ViewChild( 'companySectorTypeahead' )
    companySectorTypeahead: NgbTypeahead;
    companySectorFocus$ = new Subject<string>();
    companySectorClick$ = new Subject<string>();
    companySectorFieldTouched = false;

    constructor(
        private languageHelper: JhiLanguageHelper,
        private userService: UserService,
        private route: ActivatedRoute,
        private companySectorService: CompanySectorService,
        private registerService: Register,
        private candidateService: CandidateService,
        private companyService: CompanyService,
        private sponsoringInstitutionService: SponsoringInstitutionService
    ) { }

    ngOnInit() {
        this.isSaving = false;

        this.route.data.subscribe(( { user } ) => {
            this.authorities = [];
            this.userService.authorities().subscribe( authorities => {
                this.authorities = authorities;
                this.authorities = this.authorities.filter( auth => auth !== AuthoritiesConstants.ROLE_ADMIN && auth !== AuthoritiesConstants.ROLE_USER );
            } );
            this.languageHelper.getAll().then( languages => {
                this.languages = languages;
            } );
            pipeToResponse( this.companySectorService.findAll() ).subscribe(
                ( res: ICompanySector[] ) => { this.companysectors = res; this.updateLabelSector(); } );

            this.user = user.body ? user.body : user;

            this.createMode = isNullOrUndefined( this.user.id );
            if ( this.user && this.user.authorities && this.user.authorities.length > 0 ) {
                this.currentAuthority = this.user.authorities[0];
                this.loadRoleDetail( this.user.currentRoleId, this.currentAuthority );
            }
        } );
    }

    previousState() {
        window.history.back();
    }

    onSelectCompanySize( event: any ) {
        this.userRoleDetail.numberOfEmployee = null;
    }

    onRoleChanged( role: string ) {
        this.loadRoleDetail( this.user.currentRoleId, role );
    }

    loadRoleDetail( id: number, role: string ) {
        if ( role === AuthoritiesConstants.ROLE_CANDIDATE ) {
            this.userRoleDetail = new RegistrationCandidateUser();
        } else if ( role === AuthoritiesConstants.ROLE_COMPANY ) {
            this.userRoleDetail = new RegistrationCompanyUser();
        } else if ( role === AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION ) {
            this.userRoleDetail = new RegistrationSponsoringInstitutionUser();
        }

        if ( id ) {
            if ( role === AuthoritiesConstants.ROLE_CANDIDATE ) {
                pipeToResponse( this.candidateService.find( id ) ).subscribe(
                    candidate => this.userRoleDetail = candidate
                );
            } else if ( role === AuthoritiesConstants.ROLE_COMPANY ) {
                pipeToResponse( this.companyService.find( id ) ).subscribe(
                    company => {
                        this.userRoleDetail = company;
                        this.updateLabelSector();
                    }
                );
            } else if ( role === AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION ) {
                pipeToResponse( this.sponsoringInstitutionService.find( id ) ).subscribe(
                    institution => this.userRoleDetail = institution
                );
            }
        }
    }

    updateLabelSector() {
        if ( this.userRoleDetail && this.userRoleDetail.sectorId && this.companysectors ) {
            this.setCompanySector( this.companysectors.find( sect => sect.id === this.userRoleDetail.sectorId ) );
        }
    }

    save() {
        this.isSaving = true;

        if ( !this.createMode ) {
            this.userService.update( this.user ).subscribe( response => this.onSaveSuccess( response ), err => this.processError( err ) );
        } else {
            this.passwNotMatch = this.user.password !== this.confirmPassword;
            if ( this.passwNotMatch ) {
                return;
            }

            this.userRoleDetail.login = this.user.login;
            this.userRoleDetail.firstName = this.user.firstName;
            this.userRoleDetail.lastName = this.user.lastName;
            this.userRoleDetail.email = this.user.email;
            this.userRoleDetail.langKey = this.user.langKey;
            this.userRoleDetail.roleAccount = this.currentAuthority;
            this.userRoleDetail.password = this.user.password;

            this.registerService.save( this.userRoleDetail ).subscribe(
                res => this.onSaveSuccess( res ),
                err => this.processError( err )
            );
        }
    }

    private processError( response: HttpErrorResponse ) {
        this.errorUserExists = response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE;
        this.errorEmailExists = response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE;
        this.onSaveError();
    }

    searchCompanySector = ( text$: Observable<string> ) => {
        const debouncedText$ = text$.pipe( debounceTime( 200 ), distinctUntilChanged() );

        const clicksWithClosedPopup$ = this.companySectorClick$.pipe(
            filter(() => !this.companySectorTypeahead.isPopupOpen() ),
            tap(() => this.companySectorFieldTouched = true ) );

        const inputFocus$ = this.companySectorFocus$.pipe( tap(() => this.companySectorFieldTouched = true ) );

        return merge( debouncedText$, inputFocus$, clicksWithClosedPopup$ ).pipe(
            map(( term: string ) => {
                term = term.trim();

                const companyySectorFound = this.companysectors.find( sector =>
                    this.getFormattedCompanySector( sector ) === term );

                if ( !companyySectorFound ) {
                    this.userRoleDetail.sectorId = null;
                }

                return ( term === '' ?
                    this.getAvailableSectors() :
                    this.getAvailableSectors()
                        .filter( v => v.toLowerCase().indexOf( term.toLowerCase() ) > -1 )
                );
            } )
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
        this.userRoleDetail.sectorId = sectorFound.id;
        this.companySectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }

    private onSaveSuccess( result ) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
