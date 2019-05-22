import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgForm, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { CompanyDeleteJobOfferDialogComponent } from 'app/entities/company/company-delete-job-offer-dialog.component';
import { CompanyUpdateJobOfferDialogComponent } from 'app/entities/company/company-update-job-offer-dialog.component';
import { CompanyChangeStateJobOfferDialogComponent } from 'app/entities/company/company-change-state-job-offer-dialog.component';
import { CompanyService } from 'app/entities/company/company.service';
import { JobOfferService } from 'app/entities/job-offer/job-offer.service';
import { ProjectService } from 'app/entities/project/project.service';
import { isValidId, ITEMS_PER_PAGE, observableArrayToPromiseNoThrow, pipeToResponse } from 'app/shared';
import { ModalService } from 'app/shared/modal/modal.service';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { CompanyInfo } from 'app/shared/model/company.model';
import { IJobOffer, JobOfferStatus, JobOfferSearchFilter, JobOffer } from 'app/shared/model/job-offer.model';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Subscription, Observable, of, Subject, merge } from 'rxjs';
import { catchError, debounceTime, filter, map, switchMap, distinctUntilChanged, tap } from 'rxjs/operators';
import { AppliedJobService } from 'app/entities/applied-job/applied-job.service';
import { ApplyToJobRequest, IAppliedJob } from 'app/shared/model/applied-job.model';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { isNullOrUndefined } from 'util';
import { NgbTypeahead, NgbTypeaheadSelectItemEvent, NgbAccordion } from '@ng-bootstrap/ng-bootstrap';
import { ISkillTag, SkillType } from 'app/shared/model/skill-tag.model';
import { Country } from 'app/shared/model/candidate.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { SkillTagService } from 'app/entities/skill-tag/skill-tag.service';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from 'app/entities/skill/skill.service';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';

@Component( {
    selector: 'jhi-jobs-offer-list',
    templateUrl: './jobs-offer-list.component.html'
} )
export class JobsOfferListComponent implements OnInit, OnDestroy, AfterViewInit {
    jobsOffer: JobOffer[];
    jobSelected: IJobOffer;
    candidateSkills: ISkill[];
    appliedJobCandidate: IAppliedJob[];
    jobCompanyInfo: CompanyInfo;
    totalItems: any;
    itemsPerPage = ITEMS_PER_PAGE;
    page = 1;
    previousPage: number;
    loadingJobSelected = false;
    listModifiedEventSubscriber: Subscription;
    selectedDeletedEventSubscriber: Subscription;
    selectedModifiedEventSubscriber: Subscription;
    isMobile: boolean;
    currentUserAuthority: string;
    searchMode = false;

    // filters
    filters: JobOfferSearchFilter = {};

    // select nazioni
    countries = Object.keys( Country );

    // Skills Tag
    skillTags: ISkillTag[];
    // Settori
    companysectors: ICompanySector[];

    @ViewChild( 'searchForm' )
    searchForm: NgForm;
    @ViewChild( 'acc' )
    ngbAccordion: NgbAccordion;

    selectMode = false;

    constructor( protected jobOfferService: JobOfferService,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected activatedRoute: ActivatedRoute,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected modalService: ModalService,
        protected companySectorsService: CompanySectorService,
        protected projectService: ProjectService,
        protected companyService: CompanyService,
        protected appliedJobService: AppliedJobService,
        protected jhiAlertService: JhiAlertService,
        protected searchFilterParamsService: SearchFilterParamsService,
        protected skillTagService: SkillTagService,
        protected companySectorService: CompanySectorService,
        protected skillService: SkillService,
        protected userSessionStorageService: UserSessionStorageService ) {

        this.activatedRoute.data.subscribe( data => {
            if ( data.searchMode === true ) {
                this.searchMode = data.searchMode;
            }
        } );

    }

    ngOnInit() {
        this.currentUserAuthority = this.accountService.getCurrentRole();
        this.isMobile = window.screen.width < 600;
        this.listModifiedEventSubscriber = this.eventManager.subscribe( 'jobsOfferModified', response => this.reloadJobsOffer() );
        this.selectedDeletedEventSubscriber = this.eventManager.subscribe( 'jobOfferDeleted', response => this.refreshJobSelectedOnDelete() );
        this.selectedModifiedEventSubscriber = this.eventManager.subscribe( 'jobOfferModified', response => this.reloadJobSelected() );
    }

    ngAfterViewInit() {
        setTimeout(() => {
            if ( this.accountService.isCurrentRoleCandidate() ) {
                this.initCandidateSkills( this.accountService.getCurrentRoleId() );
            }
            this.initFilters();
            this.reloadJobsOffer();
        }, 0 );
    }

    refreshJobSelectedOnDelete() {
        for ( let i = 0; i < this.jobsOffer.length; i++ ) {
            if ( this.jobsOffer[i].id === this.jobSelected.id ) {
                this.jobsOffer.splice( i, 1 );
            }
        }
        this.resetJobSelected();
    }

    onClickSeleziona( $event, value: boolean ) {
        this.stopPropagation( $event );
        this.selectMode = value;
    }

    onClickSubmitPromote( $event ) {
        this.stopPropagation( $event );
        const offersToPromote = this.jobsOffer.filter( val => val.selectedInList === true );
        if ( offersToPromote.length === 0 ) {
            alert( 'Seleziona almeno un offerta' );
            return;
        }

        this.gotoPromotePage( offersToPromote );
    }

    onClickSelectAll( $event ) {
        this.stopPropagation( $event );
        this.jobsOffer.forEach( x => x.selectedInList = true );
    }

    onClickSubmitSingleOffer( jobOffer: IJobOffer ) {
        this.gotoPromotePage( [jobOffer] );
    }

    gotoPromotePage( jobsOffer: IJobOffer[] ) {
        this.userSessionStorageService.setupJobOfferPromoteData( jobsOffer );
        this.router.navigate( ['job-offer', 'promote'] );
    }

    stopPropagation( $event ) {
        $event.preventDefault();
        $event.stopPropagation();
    }

    reloadJobsOffer() {
        const queryParams: any = {
            page: this.page - 1,
            size: this.itemsPerPage,
            'sort': ['startDate,desc']
        };
        if ( this.filters ) {
            if ( this.filters.jobTitle ) {
                queryParams['jobTitle.contains'] = this.filters.jobTitle;
            }
            if ( this.filters.skillsTag && this.filters.skillsTag.length > 0 ) {
                const skillTagIds = this.filters.skillsTag.map( skillTag => skillTag.id );
                queryParams['skillTagId.in'] = skillTagIds;
                this.searchForm.form.controls['skillTags'] = new FormControl( this.filters.skillsTag );
            }
            if ( this.filters.jobCity ) {
                queryParams['jobCity.contains'] = this.filters.jobCity;
            }
            if ( this.filters.jobCountry ) {
                queryParams['jobCountry.equals'] = this.filters.jobCountry;
            }
            if ( this.filters.employmentType ) {
                queryParams['employmentType.equals'] = this.filters.employmentType;
            }
            if ( this.filters.seniorityLevel ) {
                queryParams['seniorityLevel.equals'] = this.filters.seniorityLevel;
            }
            if ( this.filters.jobOfferType ) {
                queryParams['jobOfferType.equals'] = this.filters.jobOfferType;
            }
            if ( this.filters.startDate && this.filters.startDate.isValid() ) {
                queryParams['startDate.greaterOrEqualThan'] = this.filters.startDate.format( DATE_FORMAT );
            }
            if ( !this.isCurrentRoleCandidate() ) {
                const status: JobOfferStatus[] = [];
                if ( this.filters.statusDraft ) {
                    status.push( JobOfferStatus.DRAFT );
                }
                if ( this.filters.statusActive ) {
                    status.push( JobOfferStatus.ACTIVE );
                }
                if ( this.filters.statusEnded ) {
                    status.push( JobOfferStatus.ENDED );
                }
                if ( status && status.length > 0 ) {
                    queryParams['status.in'] = status;
                }
            }
            if ( this.filters.companyId ) {
                queryParams['companyId.equals'] = this.filters.companyId;
            }
            if ( this.filters.sectorId ) {
                queryParams['sectorId.equals'] = this.filters.sectorId;
                this.searchForm.form.controls['sectorId'] = new FormControl( this.filters.sectorId );
            }
            queryParams['searchMode'] = this.searchMode;
        }

        this.searchFilterParamsService.storePathFormParams( this.router.url, this.searchForm.form );

        this.jobOfferService.query( queryParams ).subscribe(
            ( res: HttpResponse<IJobOffer[]> ) => {
                this.resetJobSelected();
                this.paginateJobsOffer( res.body, res.headers );
                this.reloadAppliedJobs();
                if ( !this.isMobile ) {
                    if ( this.jobsOffer && this.jobsOffer.length > 0 ) {
                        this.setJobSelected( this.jobsOffer[0].id );
                    }
                }
            }
        );
        this.ngbAccordion.collapse( 'jobOfferSearchFiltersPanel' );
    }

    resetJobSelected() {
        this.jobSelected = null;
        this.jobCompanyInfo = null;
    }

    reloadAppliedJobs() {
        if ( this.currentUserAuthority === AuthoritiesConstants.ROLE_CANDIDATE ) {
            if ( !this.appliedJobCandidate ) {
                pipeToResponse( this.appliedJobService.query( { 'candidateId.equals': this.accountService.getCurrentRoleId() } ) )
                    .subscribe(( resAppliedJobs: IAppliedJob[] ) => {
                        this.appliedJobCandidate = resAppliedJobs;
                        this.refreshAppliedJobs();
                    } );
            } else {
                this.refreshAppliedJobs();
            }
        }
    }

    refreshAppliedJobs() {
        if ( this.appliedJobCandidate && this.appliedJobCandidate.length > 0 ) {
            this.jobsOffer.forEach( job => {
                job.candidateApplied = false;
                const appliedJob = this.appliedJobCandidate.find( aj => aj.jobOfferId === job.id );
                if ( appliedJob ) {
                    job.candidateApplied = true;
                }
            } );
        } else {
            this.jobsOffer.forEach( job => job.candidateApplied = false );
        }
    }

    //    statusOffersAvailable() {
    //        return this.currentUserAuthority === AuthoritiesConstants.ROLE_CANDIDATE || this.currentUserAuthority === AuthoritiesConstants.ROLE_USER ?
    //            [JobOfferStatus.ACTIVE] : [JobOfferStatus.ACTIVE, JobOfferStatus.DRAFT, JobOfferStatus.ENDED];
    //    }

    reloadJobSelected() {
        if ( !this.isMobile ) {
            pipeToResponse( this.jobOfferService.find( this.jobSelected.id ) )
                .subscribe(( res: IJobOffer ) => {
                    res.imageUrl = this.jobSelected.imageUrl;
                    this.jobSelected = res;
                    for ( let i = 0; i < this.jobsOffer.length; i++ ) {
                        if ( this.jobsOffer[i].id === res.id ) {
                            this.jobsOffer[i] = res;
                        }
                    }
                } );
        }
    }

    loadPage( page: number ) {
        if ( page !== this.previousPage ) {
            this.previousPage = page;
            this.reloadJobsOffer();
        }
    }

    protected paginateJobsOffer( data: IJobOffer[], headers: HttpHeaders ) {
        this.totalItems = parseInt( headers.get( 'X-Total-Count' ), 10 );
        this.jobsOffer = data;
    }

    ngOnDestroy(): void {
        this.eventManager.destroy( this.listModifiedEventSubscriber );
        this.eventManager.destroy( this.selectedDeletedEventSubscriber );
        this.eventManager.destroy( this.selectedModifiedEventSubscriber );
    }

    setJobSelected( jobId: number ) {
        if ( !this.isMobile ) {
            if ( !this.isJobOfferSelected( jobId ) ) {
                this.resetJobSelected();
                this.loadingJobSelected = true;
                this.jobOfferService.find( jobId ).subscribe(
                    ( res: HttpResponse<IJobOffer> ) => {
                        this.jobSelected = res.body;
                        this.loadingJobSelected = false;
                        if ( !this.jobSelected.applymentCount ) {
                            pipeToResponse( this.appliedJobService.count( { 'jobOfferId.equals': this.jobSelected.id } ) ).subscribe(
                                ( count: number ) => {
                                    this.jobSelected.applymentCount = count;
                                }
                            );
                        }

                        pipeToResponse( this.companyService.findInfo( this.jobSelected.companyId ) ).subscribe(
                            ( info: CompanyInfo ) => {
                                this.jobCompanyInfo = info;
                            }
                        );
                        if ( this.jobSelected.sectorId ) {
                            this.companySectorService.findDescriptionById( this.jobSelected.sectorId ).subscribe(
                                ( desc: string ) => this.jobSelected.sectorDescription = desc
                            );
                            //                        this.companySectorService.find( this.jobSelected.sectorId ).subscribe(
                            //                            ( cs: HttpResponse<ICompanySector> ) => this.jobSelected.sectorDescription = cs.body.description
                            //                        );
                        }
                        if ( this.accountService.isCurrentRoleCandidate() ) {
                            if ( this.candidateSkills ) {
                                this.jobSelected.skills.map( jobSkill => {
                                    const candidateSkill = this.candidateSkills.find( cs => cs.skillTagId === jobSkill.skillTagId );
                                    if ( candidateSkill ) {
                                        jobSkill.candidateOwner = true;
                                    }
                                } );
                                this.sortSkills( this.jobSelected.skills );
                            }
                            // verifica se il candiadte e' gia' candiadto all'offerta selezionata
                            if ( this.appliedJobCandidate ) {
                                const appliedJob = this.appliedJobCandidate.find( aj => aj.jobOfferId === this.jobSelected.id );
                                if ( appliedJob ) {
                                    this.jobSelected.candidateApplied = true;
                                }
                            }
                        }

                    },
                    ( err: HttpErrorResponse ) => {
                        this.jhiAlertService.error( err.message );
                        this.loadingJobSelected = false;
                    }
                );
            }
        } else {
            this.router.navigate( ['job-offer', jobId, 'view'] );
        }
    }

    sortSkills( skills: IJobOfferSkill[] ) {
        skills.sort(( a: IJobOfferSkill, b: IJobOfferSkill ) => {
            if ( a.candidateOwner && !b.candidateOwner ) {
                return -1;
            } else if ( b.candidateOwner && !a.candidateOwner ) {
                return 1;
            } else {
                return 0;
            }
        } );
    }

    async onModifyClick() {
        const isSaving = !isValidId( this.jobSelected.companyId );
        const companySectors = await observableArrayToPromiseNoThrow( pipeToResponse( this.companySectorsService.findAll() ) );
        const projects = await observableArrayToPromiseNoThrow(
            pipeToResponse( this.projectService.query( { 'companyId.equals': this.jobSelected.companyId } ) ) );

        const params = {
            'companyId': this.jobSelected.companyId,
            'jobOffer': Object.assign( {}, this.jobSelected ),
            'companySectors': companySectors,
            'projects': projects,
            'isSaving': isSaving
        };

        this.modalService.openHugeModal( CompanyUpdateJobOfferDialogComponent, params )
            .then( res => { } )
            .catch( err => { } );
    }

    onDeleteClick() {
        const params = {
            'jobOffer': this.jobSelected
        };

        this.modalService.openHugeModal( CompanyDeleteJobOfferDialogComponent, params )
            .then( res => { } )
            .catch( err => { } );
    }

    onPublishOfferClick() {
        const params = {
            'jobOffer': this.jobSelected,
            'status': JobOfferStatus.ACTIVE,
        };

        this.modalService.openHugeModal( CompanyChangeStateJobOfferDialogComponent, params )
            .then( res => { } )
            .catch( err => { } );
    }

    onEndOfferClick() {
        const params = {
            'jobOffer': this.jobSelected,
            'status': JobOfferStatus.ENDED,
        };

        this.modalService.openHugeModal( CompanyChangeStateJobOfferDialogComponent, params )
            .then( res => { } )
            .catch( err => { } );
    }

    onApplyJobClick() {
        if ( this.currentUserAuthority === AuthoritiesConstants.ROLE_CANDIDATE ) {
            const id = this.accountService.getCurrentRoleId();
            pipeToResponse( this.appliedJobService.applyToJob( new ApplyToJobRequest( id, this.jobSelected.id ) ) ).subscribe(
                res => {
                    res.jobOfferId = this.jobSelected.id;
                    res.candidateId = id;
                    this.appliedJobCandidate.push( res );
                    this.jobSelected.candidateApplied = true;
                    this.refreshAppliedJobs();
                    this.userSessionStorageService.clear( 'countApplymentsCandidate' );
                }
            );
        }
    }

    previousState() {
        window.history.back();
    }

    initFilters() {
        this.filters = new JobOfferSearchFilter();
        this.searchFilterParamsService.patchFormWithParams( this.router.url, this.searchForm.form );
        if ( this.searchForm.form.controls && this.searchForm.form.controls.companyId ) {
            this.filters.companyId = this.searchForm.form.controls.companyId.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.employmentType ) {
            this.filters.employmentType = this.searchForm.form.controls.employmentType.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.city ) {
            this.filters.jobCity = this.searchForm.form.controls.city.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.country ) {
            this.filters.jobCountry = this.searchForm.form.controls.country.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.jobOfferType ) {
            this.filters.jobOfferType = this.searchForm.form.controls.jobOfferType.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.jobTitle ) {
            this.filters.jobTitle = this.searchForm.form.controls.jobTitle.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.sectorId ) {
            this.filters.sectorId = this.searchForm.form.controls.sectorId.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.seniorityLevel ) {
            this.filters.seniorityLevel = this.searchForm.form.controls.seniorityLevel.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.startDate ) {
            this.filters.startDate = this.searchForm.form.controls.startDate.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.statusActive ) {
            this.filters.statusActive = this.searchForm.form.controls.statusActive.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.statusDraft ) {
            this.filters.statusDraft = this.searchForm.form.controls.statusDraft.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.statusEnded ) {
            this.filters.statusEnded = this.searchForm.form.controls.statusEnded.value;
        }
        if ( this.searchForm.form.controls && this.searchForm.form.controls.skillTags ) {
            this.filters.skillsTag = this.searchForm.form.controls.skillTags.value;
        }
        this.loadJobOfferSectors();
    }

    resetFilters() {
        this.filters = new JobOfferSearchFilter();
        this.searchFilterParamsService.clearPathFormParams( this.router.url );
    }

    searchSkills = ( text$: Observable<string> ) =>
        text$.pipe(
            debounceTime( 200 ),
            switchMap( term => ( ( this.filters.skillTagName = term ) === '' ) ? of( [] ) :
                this.skillTagService.query( { 'name.contains': term, 'type.equals': SkillType.OTHER, 'size': 6, 'sort': ['name,asc'] } ).pipe(
                    map(( skillTags: HttpResponse<ISkillTag[]> ) => ( this.skillTags = skillTags.body ).map( skill => this.skillTagFormatter( skill ) ) ),
                    catchError(() => of( this.skillTags = [] ) )
                )
            ),
        );

    skillTagFormatter = ( skillTag: ISkillTag ) => !isNullOrUndefined( skillTag ) ? skillTag.name : '';

    onSelectSkillTag( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();

        const skillTagFound = this.skillTags.find( skillTag =>
            this.skillTagFormatter( skillTag ) === ngbTypeaheadSelectItem.item );

        if ( skillTagFound ) {
            this.filters.skillTagName = '';
            if ( this.filters.skillsTag.indexOf( skillTagFound ) === -1 ) {
                this.filters.skillsTag.push( skillTagFound );
            }
        }

    }

    removeSkillTag( skillTag: ISkillTag ) {
        const index = this.filters.skillsTag.indexOf( skillTag, 0 );
        if ( index > -1 ) {
            this.filters.skillsTag.splice( index, 1 );
        }
    }

    loadJobOfferSectors() {
        this.companySectorService.findAll().subscribe(
            ( res: HttpResponse<ICompanySector[]> ) => {
                this.companysectors = res.body;
                if ( this.searchForm && this.searchForm.controls.sectorId ) {
                    const sectorId = +this.searchForm.controls.sectorId;
                    const sector = this.companysectors.find( cs => cs.id === sectorId );
                    if ( sector ) {
                        this.setJobOfferSector( sector );
                    }
                }
            },
            ( res: HttpErrorResponse ) => this.jhiAlertService.error( res.message, null, null )
        );
    }

    searchJobOfferSector = ( text$: Observable<string> ) => {
        const debouncedText$ = text$.pipe( debounceTime( 200 ), distinctUntilChanged() );

        return merge( debouncedText$ ).pipe(
            map( term => {
                term = term.trim();
                const companyySectorFound = this.companysectors.find( sector =>
                    this.getFormattedJobOfferSector( sector ) === term );

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
        return this.companysectors.map( sector => this.getFormattedJobOfferSector( sector ) );
    }

    private getFormattedJobOfferSector( sector: ICompanySector | any ): string {
        return `${sector.description}`;
    }

    onSelectJobOfferSector( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        const sectorFound = this.companysectors.find( companySector =>
            this.getFormattedJobOfferSector( companySector ) === ngbTypeaheadSelectItem.item );
        if ( sectorFound ) {
            this.setJobOfferSector( sectorFound );
        }
    }

    private setJobOfferSector( sectorFound ) {
        this.filters.sectorId = sectorFound.id;
        this.filters.jobOfferSectorName = this.getFormattedJobOfferSector( sectorFound );
    }

    initCandidateSkills( candidateId: number ) {
        pipeToResponse( this.skillService.findAll( candidateId ) ).subscribe(( res: ISkill[] ) => this.candidateSkills = res );
    }

    numberOfCandidateSkillOwner() {
        let numberOfCandidateSkillOwner = 0;
        if ( this.jobSelected && this.jobSelected.skills && this.jobSelected.skills.length > 0 ) {
            numberOfCandidateSkillOwner = this.jobSelected.skills.filter( skill => skill.candidateOwner ).length;
        }
        return numberOfCandidateSkillOwner;
    }

    isCurrentRoleCompany() {
        return this.accountService.isCurrentRoleCompany();
    }

    isCurrentRoleCandidate() {
        return this.accountService.isCurrentRoleCandidate();
    }

    isCurrentRoleAdmin() {
        return this.accountService.isCurrentRoleAdmin();
    }

    isCurrentRoleSponsoringInstitution() {
        return this.accountService.isCurrentRoleSponsoringInstitution();
    }

    isJobOfferSelected( jobOfferId: number ): boolean {
        return this.jobSelected && this.jobSelected.id === jobOfferId;
    }

}
