import { PlatformLocation } from '@angular/common';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal, NgbTypeaheadSelectItemEvent, NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { IJobOffer, JobOfferStatus, JobOfferType } from 'app/shared/model/job-offer.model';
import { IProject } from 'app/shared/model/project.model';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subject, Observable, of, merge } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map, tap, switchMap, catchError } from 'rxjs/operators';
import { JobOfferService } from '../job-offer/job-offer.service';
import * as moment from 'moment';
import { SkillType, ISkillTag } from 'app/shared/model/skill-tag.model';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { SkillTagService } from '../skill-tag';
import { Country } from 'app/shared/model/candidate.model';
import { CompanySectorService } from 'app/entities/company-sector';

@Component( {
    selector: 'jhi-company-update-job-offer-dialog',
    templateUrl: './company-update-job-offer-dialog.component.html'
} )
export class CompanyUpdateJobOfferDialogComponent implements OnInit {
    companyId: number;
    countries = Object.keys( Country );
    jobOffer: IJobOffer;
    skillTagTemp = '';
    isSaving: boolean;
    companySectors: ICompanySector[];
    projects: IProject[];
    jobOfferTypes = Object.keys( JobOfferType );

    @ViewChild( 'jobOfferSectorTypeahead' )
    jobOfferSectorTypeahead: NgbTypeahead;
    jobOfferSectorFocus$ = new Subject<string>();
    jobOfferSectorClick$ = new Subject<string>();
    jobOfferSectorFieldTouched = false;

    constructor(
        protected jobOfferService: JobOfferService,
        protected activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected skillTagService: SkillTagService,
        protected companySectorService: CompanySectorService,
        private location: PlatformLocation ) {
        location.onPopState(() => this.activeModal.dismiss() );
    }

    ngOnInit() {
        if ( !this.jobOffer.id ) {
            this.jobOffer.skills = [];
        }
        this.loadCompanySectors();
    }

    loadCompanySectors() {
        this.companySectorService.findAll().subscribe(
            ( res: HttpResponse<ICompanySector[]> ) => {
                this.companySectors = res.body;
                if ( this.jobOffer.sectorId ) {
                    const sector = this.companySectors.find( cs => cs.id === this.jobOffer.sectorId );
                    if ( sector ) {
                        this.setCompanySector( sector );
                    }
                }
            },
            ( err: HttpErrorResponse ) => this.onError( err.message )
        );
    }

    save() {
        this.isSaving = true;

        if ( this.jobOffer.id !== undefined ) {
            this.subscribeToSaveResponse( this.jobOfferService.update( this.jobOffer ) );
        } else {
            this.jobOffer.status = JobOfferStatus.DRAFT;
            this.jobOffer.companyId = this.companyId;
            this.jobOffer.startDate = moment();
            this.jobOffer.enabled = true;
            this.subscribeToSaveResponse( this.jobOfferService.create( this.jobOffer ) );
        }
    }

    protected subscribeToSaveResponse( result: Observable<HttpResponse<IJobOffer>> ) {
        result.subscribe(( res: HttpResponse<IJobOffer> ) => this.onSaveSuccess(), ( res: HttpErrorResponse ) => this.onSaveError() );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.eventManager.broadcast( {
            name: 'jobOfferModified',
            content: 'Modified the job offer'
        } );
        this.activeModal.dismiss( true );
    }

    clear() {
        this.activeModal.dismiss( 'cancel' );
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

    protected onSaveError() {
        this.isSaving = false;
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

                const companyySectorFound = this.companySectors.find( sector =>
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
        return this.companySectors.map( sector => this.getFormattedCompanySector( sector ) );
    }

    private getFormattedCompanySector( sector: ICompanySector | any ): string {
        return `${sector.description}`;
    }

    onSelectJobOfferSector( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        const sectorFound = this.companySectors.find( companySector =>
            this.getFormattedCompanySector( companySector ) === ngbTypeaheadSelectItem.item );
        if ( sectorFound ) {
            this.setCompanySector( sectorFound );
        }
    }

    private setCompanySector( sectorFound ) {
        this.jobOffer.sectorId = sectorFound.id;
        this.jobOfferSectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
    }
}

/*
@Component({
    selector: 'jhi-company-update-job-offer-popup',
    template: ''
} )
export class CompanyUpdateJobOfferPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor( protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal,
        protected projectService: ProjectService, protected accountService: AccountService ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { jobOffer, companySectors } ) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open( CompanyUpdateJobOfferDialogComponent as Component, { size: 'lg', backdrop: 'static' } );
                this.ngbModalRef.componentInstance.jobOffer = jobOffer;
                this.ngbModalRef.componentInstance.companySectors = companySectors;
                const companyId = this.accountService.getCurrentRoleId();
                this.ngbModalRef.componentInstance.companyId = companyId;
                this.ngbModalRef.componentInstance.isSaving = !( isValidId( jobOffer.companyId ) || isValidId( companyId ) );

                pipeToResponse( this.projectService.query( { 'companyId.equals': isValidId( jobOffer.companyId ) ? jobOffer.companyId : companyId } ) )
                    .subscribe(
                    ( res: IProject[] ) => this.ngbModalRef.componentInstance.projects = res,
                    ( res: HttpErrorResponse ) => this.ngbModalRef.componentInstance.projects = []
                    );

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
