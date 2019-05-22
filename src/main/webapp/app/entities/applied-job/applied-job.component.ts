import { Component, OnInit, OnDestroy, ViewChild, AfterViewInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { merge, Observable, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE, isValidId, observableVariableToPromiseNoThrow, pipeToResponse, enableFooter, disableFooter } from 'app/shared';
import { AppliedJobItem, AppiedJobStatus, AppliedJobOfferSearchFilter, AppliedJob } from 'app/shared/model/applied-job.model';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { AppliedJobService } from './applied-job.service';
import { JobOfferService } from '../job-offer/job-offer.service';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { ConfirmDialogComponent } from 'app/shared/modal/confirm-dialog.component';
import { ModalService } from 'app/shared/modal/modal.service';
import { AppliedJobUpdateStateDialogComponent } from './applied-job-update-state-dialog.component';

@Component( {
    selector: 'jhi-applied-job',
    templateUrl: './applied-job.component.html'
} )
export class AppliedJobComponent implements OnInit, OnDestroy, AfterViewInit {
    currentAccount: any;
    jobOfferId: number;
    applyments: AppliedJobItem[];
    eventSubscriber: Subscription;
    totalItems: any;
    links: any;
    routeData: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    showTable = true;

    // form ricerca
    filters: AppliedJobOfferSearchFilter = {};
    @ViewChild( 'searchForm' )
    searchForm: NgForm;

    // suggestion job offers
    jobOffers: IJobOffer[];

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected searchFilterParamsService: SearchFilterParamsService,
        protected appliedJobService: AppliedJobService,
        protected jobOfferService: JobOfferService,
        protected modalService: ModalService

    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe( data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        } );
    }

    loadAll() {
        this.search();

    }
    search() {
        const params = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };

        const appiedJobStatusIn: AppiedJobStatus[] = [];
        if ( this.filters.statusNew ) {
            appiedJobStatusIn.push( AppiedJobStatus.NEW );
        }
        if ( this.filters.statusInProgress ) {
            appiedJobStatusIn.push( AppiedJobStatus.EVALUATION_IN_PROGRESS );
        }
        if ( this.filters.statusAprroved ) {
            appiedJobStatusIn.push( AppiedJobStatus.APPROVED );
        }
        if ( this.filters.statusRejected ) {
            appiedJobStatusIn.push( AppiedJobStatus.REJECTED );
        }
        if ( appiedJobStatusIn && appiedJobStatusIn.length > 0 ) {
            params['appliedJobStatus.in'] = appiedJobStatusIn;
        }
        if ( this.jobOfferId || this.filters.jobOfferId ) {
            params['jobOfferId.equals'] = this.jobOfferId ? this.jobOfferId : this.filters.jobOfferId;
        }
        if ( this.filters.jobOfferTitle ) {
            params['jobOffer.contains'] = this.filters.jobOfferTitle;
        }

        this.searchFilterParamsService.storePathFormParams( this.router.url, this.searchForm.form );

        this.appliedJobService
            .queryItems( params )
            .subscribe(
            ( res: HttpResponse<AppliedJobItem[]> ) => this.paginateAppliedJobs( res.body, res.headers ),
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
        this.router.navigate( ['/applied-job'], {
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
            '/applied-job',
            {
                page: this.page,
                sort: this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )
            }
        ] );
        this.loadAll();
    }

    ngOnInit() {
        this.jobOfferId = this.activatedRoute.snapshot.params['id-job-offer'];
        this.registerChangeInAppliedJobs();
        enableFooter(document);
    }

    ngAfterViewInit() {
        setTimeout(() => {
            this.initFilters();
            if ( this.isCurrentRoleCompany() ) {
                this.loadJobOffers();
            }
            this.loadAll();
        }, 0 );
    }

    initFilters() {
        this.filters = new AppliedJobOfferSearchFilter();
        this.searchFilterParamsService.patchFormWithParams( this.router.url, this.searchForm.form );
    }

    resetFilters() {
        this.filters = new AppliedJobOfferSearchFilter();
        this.searchFilterParamsService.clearPathFormParams( this.router.url );
    }

    ngOnDestroy() {
        this.eventManager.destroy( this.eventSubscriber );
        disableFooter(document);
    }

    trackId( index: number, item: any ) {
        return item.id;
    }

    registerChangeInAppliedJobs() {
        this.eventSubscriber = this.eventManager.subscribe( 'appliedJobListModification', response => this.loadAll() );
    }

    sort() {
        const result = [this.predicate + ',' + ( this.reverse ? 'asc' : 'desc' )];
        if ( this.predicate !== 'id' ) {
            result.push( 'id' );
        }
        return result;
    }

    protected paginateAppliedJobs( data: AppliedJobItem[], headers: HttpHeaders ) {
        this.links = this.parseLinks.parse( headers.get( 'link' ) );
        this.totalItems = parseInt( headers.get( 'X-Total-Count' ), 10 );
        this.applyments = data;
    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
    }

    previousState() {
        window.history.back();
    }

    isCurrentRoleCandidate() {
        return this.accountService.isCurrentRoleCandidate();
    }

    isCurrentRoleCompany() {
        return this.accountService.isCurrentRoleCompany();
    }

    isCurrentRoleSponsoringInstitution() {
        return this.accountService.isCurrentRoleSponsoringInstitution();
    }

    isCurrentRoleAdmin() {
        return this.accountService.isCurrentRoleAdmin();
    }

    loadJobOffers() {
        if ( ( this.isCurrentRoleCompany() || this.isCurrentRoleSponsoringInstitution() ) && !this.jobOfferId ) {
            this.jobOfferService.findAllByCompanyId( this.accountService.getCurrentRoleId() ).subscribe(
                ( res: HttpResponse<IJobOffer[]> ) => {
                    this.jobOffers = res.body;
                    if ( this.searchForm && this.searchForm.controls.jobOffer ) {
                        const jobOfferId = +this.searchForm.controls.jobOffer;
                        const jobOffer = this.jobOffers.find( cs => cs.id === jobOfferId );
                        if ( jobOffer ) {
                            this.setJobOffer( jobOffer );
                        }
                    }
                },
                ( res: HttpErrorResponse ) => this.jhiAlertService.error( res.message, null, null )
            );
        }
    }

    async onPrendiVisioneClick( appliedJobItem: AppliedJobItem ) {
        if ( !isValidId( appliedJobItem.id ) || appliedJobItem.appiedJobStatus !== AppiedJobStatus.NEW ) {
            return;
        }
        const appliedJob = await observableVariableToPromiseNoThrow( pipeToResponse( this.appliedJobService.find( appliedJobItem.id ) ) );

        const params = {
            title: 'jobmatchApp.appliedJob.changeStateTitle',
            message: 'jobmatchApp.appliedJob.changeStateMessage',
            translationParams: { param: 'In Valutazione' }
        };

        this.modalService.openLargeModal( ConfirmDialogComponent, params )
            .then( res => {
                if ( res ) {
                    appliedJob.appiedJobStatus = AppiedJobStatus.EVALUATION_IN_PROGRESS;
                    pipeToResponse( this.appliedJobService.update( appliedJob ) ).subscribe(
                        ( newApplied: AppliedJob ) => {
                            this.fillItemFromAppliedJob( appliedJobItem, newApplied );
                        }
                    );
                }
            } ).catch( err => {

            } );
    }

    onChangeStateClick( appliedJobItem: AppliedJobItem, newState: any ) {
        if ( !isValidId( appliedJobItem.id ) || appliedJobItem.appiedJobStatus === AppiedJobStatus.NEW ) {
            return;
        }

        const params = {
            nextState: newState,
            appliedJobId: appliedJobItem.id,
            readOnly: null
        };

        this.modalService.openLargeModal( AppliedJobUpdateStateDialogComponent, params )
            .then( res => {
                if ( res ) {
                    this.fillItemFromAppliedJob( appliedJobItem, res );
                }
            } ).catch( err => {

            } );
    }

    onViewApplymentClick( appliedJobItem: AppliedJobItem ) {
        if ( !isValidId( appliedJobItem.id )
            || !( appliedJobItem.appiedJobStatus === AppiedJobStatus.APPROVED || appliedJobItem.appiedJobStatus === AppiedJobStatus.REJECTED ) ) {
            return;
        }

        const params = {
            appliedJobId: appliedJobItem.id,
            readOnly: true
        };

        this.modalService.openLargeModal( AppliedJobUpdateStateDialogComponent, params )
            .catch( err => {

            } );
    }

    fillItemFromAppliedJob( item: AppliedJobItem, appliedJob: AppliedJob ) {
        item.id = appliedJob.id;
        item.createdBy = appliedJob.createdBy;
        item.lastModifiedBy = appliedJob.lastModifiedBy;
        item.createdDate = appliedJob.createdDate;
        item.lastModifiedDate = appliedJob.lastModifiedDate;
        item.appliedJobFeedback = appliedJob.appliedJobFeedback;
        item.appiedJobStatus = appliedJob.appiedJobStatus;
        item.candidateId = appliedJob.candidateId;
        item.jobOfferId = appliedJob.jobOfferId;
    }

    searchJobOffer = ( text$: Observable<string> ) => {
        const debouncedText$ = text$.pipe( debounceTime( 200 ), distinctUntilChanged() );

        return merge( debouncedText$ ).pipe(
            map( term => {
                term = term.trim();

                const jobOfferFound = this.jobOffers.find( jo =>
                    this.getFormattedJobOffer( jo ) === term );

                if ( !jobOfferFound ) {
                    this.filters.jobOfferId = null;
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
        return this.jobOffers.map( jobOffer => this.getFormattedJobOffer( jobOffer ) );
    }

    private getFormattedJobOffer( jobOffer: IJobOffer | any ): string {
        return `${jobOffer.jobTitle}`;
    }

    onSelectJobOffer( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        const jobOfferFound = this.jobOffers.find( jobOffer =>
            this.getFormattedJobOffer( jobOffer ) === ngbTypeaheadSelectItem.item );
        if ( jobOfferFound ) {
            this.setJobOffer( jobOfferFound );
        }
    }

    private setJobOffer( jobOfferFound ) {
        this.filters.jobOfferId = jobOfferFound.id;
        this.filters.jobOfferTitle = this.getFormattedJobOffer( jobOfferFound );
    }
}
