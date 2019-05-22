import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbTypeahead, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { CompanySectorService } from 'app/entities/company-sector';
import { CompanyService } from 'app/entities/company/company.service';
import { ProjectService } from 'app/entities/project';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { ICompany } from 'app/shared/model/company.model';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { IJobOffer, JobOfferStatus, JobOfferType } from 'app/shared/model/job-offer.model';
import { Country } from 'app/shared/model/candidate.model';
import { IProject } from 'app/shared/model/project.model';
import { ISkillTag, SkillType } from 'app/shared/model/skill-tag.model';
import { Helper, isValidId } from 'app/shared/util/helper-util';
import { pipeToResponse } from 'app/shared/util/request-util';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { Subject, Observable, of, merge } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map, tap, switchMap, catchError } from 'rxjs/operators';
import { JobOfferService } from '../job-offer/job-offer.service';
import { SkillTagService } from '../skill-tag';
import * as moment from 'moment';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';

@Component( {
    selector: 'jhi-company-update-job-offer',
    templateUrl: './company-update-job-offer.component.html'
} )
export class CompanyUpdateJobOfferComponent implements OnInit, AfterViewInit {
    company: ICompany;
    jobOffer: IJobOffer;
    isSaving: boolean;
    companysectors: ICompanySector[];
    skillTagTemp = '';
    projects: IProject[];
    countries = Object.keys( Country );
    jobOfferTypes = Object.keys( JobOfferType );

    @ViewChild( 'jobOfferSectorTypeahead' )
    jobOfferSectorTypeahead: NgbTypeahead;
    jobOfferSectorFocus$ = new Subject<string>();
    jobOfferSectorClick$ = new Subject<string>();
    jobOfferSectorFieldTouched = false;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected jobOfferService: JobOfferService,
        protected accountService: AccountService,
        protected companyService: CompanyService,
        protected companySectorService: CompanySectorService,
        protected projectService: ProjectService,
        protected activatedRoute: ActivatedRoute,
        protected skillTagService: SkillTagService,
        protected helper: Helper,
        protected userSessionStorageService: UserSessionStorageService
    ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { jobOffer } ) => {
            this.jobOffer = jobOffer;
            if ( !this.jobOffer.id ) {
                this.jobOffer.skills = [];
            }
        } );
        if ( this.accountService.isCurrentRoleCompany() ) {
            pipeToResponse( this.companyService.find( this.accountService.getCurrentRoleId() ) ).subscribe(
                ( company: ICompany ) => {
                    this.company = company;
                    this.isSaving = !isValidId( company.id );
                }
            );
        } else if ( this.accountService.isCurrentRoleSponsoringInstitution() ) {
            pipeToResponse( this.companyService.findByUser() ).subscribe(
                ( company: ICompany ) => {
                    this.company = company;
                    this.isSaving = !isValidId( company.id );
                }
            );
        }

        this.loadCompanySectors();
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
            ( err: HttpErrorResponse ) => this.onError( err.message )
        );
    }

    ngAfterViewInit() {
        if ( this.company && isValidId( this.company.id ) ) {
            pipeToResponse( this.projectService.query( { 'companyId.equals': this.company.id } ) ).subscribe(
                ( project: IProject[] ) => ( this.projects = project ),
                ( err: HttpErrorResponse ) => this.onError( err.message )
            );
        }
    }

    searchSkills = ( text$: Observable<string> ) =>
        text$.pipe(
            debounceTime( 200 ),
            switchMap( term => ( term === '' ) ? of( [] ) :
                this.skillTagService.query( { 'name.contains': term, 'type.equals': SkillType.OTHER, 'size': 6, 'sort': ['name,asc'] } ).pipe(
                    map(( skillTags: HttpResponse<ISkillTag[]> ) => skillTags.body.map( skill => this.skillTagFormatter( skill ) ) ),
                    catchError(() => of( [] ) )
                ),
            )
        );

    skillTagFormatter = ( skillTag: ISkillTag ) => skillTag ? skillTag.name : '';

    onSelectSkillTag( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        if ( ngbTypeaheadSelectItem.item !== '' ) {
            const checkAlreadyExist = this.jobOffer.skills.filter( tag => tag.skillTagName === ngbTypeaheadSelectItem.item );
            if ( checkAlreadyExist.length === 0 ) {
                this.jobOffer.skills.push( { skillTagName: ngbTypeaheadSelectItem.item } );
            }

            this.skillTagTemp = '';
        }
    }

    onSubmitTextSkillTag( event: any ) {
        if ( event.keyCode === 13 ) {
            event.preventDefault();
            if ( this.skillTagTemp !== '' ) {
                const checkAlreadyExist = this.jobOffer.skills.filter( tag => tag.skillTagName.toLowerCase() === this.skillTagTemp.toLowerCase() );
                if ( checkAlreadyExist.length === 0 ) {
                    this.jobOffer.skills.push( { skillTagName: this.skillTagTemp } );
                }

                this.skillTagTemp = '';
            }
        }
    }

    deleteSkill( skill: IJobOfferSkill ) {
        const index = this.jobOffer.skills.indexOf( skill );
        this.jobOffer.skills.splice( index, 1 );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;

        if ( this.jobOffer.id !== undefined ) {
            this.subscribeToSaveResponse( this.jobOfferService.update( this.jobOffer ) );
        } else {
            this.jobOffer.companyId = this.company.id;
            this.jobOffer.status = JobOfferStatus.DRAFT;
            this.jobOffer.startDate = moment();
            this.jobOffer.enabled = true;
            this.subscribeToSaveResponse( this.jobOfferService.create( this.jobOffer ) );
            this.userSessionStorageService.clear( 'countNewJobOffers' );
        }
    }

    protected subscribeToSaveResponse( result: Observable<HttpResponse<IJobOffer>> ) {
        result.subscribe(( res: HttpResponse<IJobOffer> ) => this.onSaveSuccess(), ( res: HttpErrorResponse ) => this.onSaveError() );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
    }
    trackCompanySectorById( index: number, item: ICompanySector ) {
        return item.id;
    }

    trackProjectById( index: number, item: IProject ) {
        return item.id;
    }

    searchJobOfferSector = ( text$: Observable<string> ) => {
        const debouncedText$ = text$.pipe( debounceTime( 200 ), distinctUntilChanged() );

        const clicksWithClosedPopup$ = this.jobOfferSectorClick$.pipe(
            filter(() => !this.jobOfferSectorTypeahead.isPopupOpen() ),
            tap(() => this.jobOfferSectorFieldTouched = true ) );

        const inputFocus$ = this.jobOfferSectorFocus$.pipe( tap(() => this.jobOfferSectorFieldTouched = true ) );

        return merge( debouncedText$, inputFocus$, clicksWithClosedPopup$ ).pipe(
            map( term => {
                term = term.trim();

                const companyySectorFound = this.companysectors.find( sector =>
                    this.getFormattedCompanySector( sector ) === term );

                if ( !companyySectorFound ) {
                    this.jobOffer.sectorId = null;
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

    onSelectJobOfferSector( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        const sectorFound = this.companysectors.find( companySector =>
            this.getFormattedCompanySector( companySector ) === ngbTypeaheadSelectItem.item );
        if ( sectorFound ) {
            this.setCompanySector( sectorFound );
        }
    }

    private setCompanySector( sectorFound ) {
        this.jobOffer.sectorId = sectorFound.id;
        this.jobOfferSectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }
}
