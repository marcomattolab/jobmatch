import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';
import { AppliedJobService } from 'app/entities/applied-job/applied-job.service';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { CompanyService } from 'app/entities/company/company.service';
import { JobOfferService } from 'app/entities/job-offer/job-offer.service';
import { ProjectService } from 'app/entities/project/project.service';
import { SkillTagService } from 'app/entities/skill-tag/skill-tag.service';
import { SkillService } from 'app/entities/skill/skill.service';
import { ModalService } from 'app/shared/modal/modal.service';
import { CandidateItem, ICandidate } from 'app/shared/model/candidate.model';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { SearchFilterParamsService } from 'app/shared/search-filter-params/search-filter-params.service';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CandidateService } from '../candidate';
import { JobOffersPromoteConfirmComponent } from './job-offers-promote-confirm.component';
import { observableArrayToPromiseNoThrow, pipeToResponse } from 'app/shared';

@Component( {
    selector: 'jhi-jobs-offer-promote',
    templateUrl: './jobs-offer-promote.component.html'
} )
export class JobsOfferPromoteComponent implements OnInit {
    jobsOfferToPromote: IJobOffer[];
    candidates: CandidateItem[];
    previousCandidatesAdded: ICandidate[];

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
        protected userSessionStorageService: UserSessionStorageService,
        protected candidateService: CandidateService ) { }

    ngOnInit(): void {
        this.loadJobOffersOrQuit();
        this.loadCandidatesAndLastCandidates();
    }

    loadJobOffersOrQuit() {
        this.jobsOfferToPromote = this.userSessionStorageService.retrieveJobOffersToPromote();
        if ( !this.jobsOfferToPromote || this.jobsOfferToPromote.length === 0 ) {
            this.back();
        }
    }

    loadCandidatesAndLastCandidates() {
        this.previousCandidatesAdded = this.userSessionStorageService.retrieveLastCandidatesSelected();
        this.candidates = this.userSessionStorageService.retrieveCandidatesSelected();

        for ( const prev of this.previousCandidatesAdded ) {
            this.candidates.pushIf( prev, el => el.id !== prev.id );
        }
    }

    keepCandidates() {
        this.userSessionStorageService.storeCandidatesSelected( this.candidates );
        this.userSessionStorageService.storeLastCandidatesSelected( this.previousCandidatesAdded );
    }

    undoLastCandidatesAdded() {
        for ( const prev of this.previousCandidatesAdded ) {
            this.candidates.removeUniqueIf( el => el.id === prev.id );
        }

        this.previousCandidatesAdded = [];
    }

    onClickDeleteCandidate( candidate: ICandidate ) {
        this.candidates.remove( candidate );
    }

    transition() {

    }

    deleteJobOffer( jobOffer: IJobOffer ) {
        if ( this.jobsOfferToPromote.length === 1 ) {
            alert( 'You cannot delete the last job offer, minimum number of promoted offers it s 1' );
            return;
        }

        this.jobsOfferToPromote.remove( jobOffer );
    }

    async promoteJobOffers() {
        const customMessages = await observableArrayToPromiseNoThrow( pipeToResponse( this.jobOfferService.findCustomPromoteJobOfferMessages() ) );

        const params = {
            candidatesId: this.candidates.map( cand => cand.id ),
            jobOffersId: this.jobsOfferToPromote.map( job => job.id ),
            customMessages
        };

        this.modalService.openLargeModal( JobOffersPromoteConfirmComponent, params )
            .then( res => {
                if ( res ) {
                    // tutte inviate
                } else {
                    // alcune scartate
                }

                this.router.navigate( ['job-offer', 'search'] );
            } ).catch( err => {

            } );
    }

    canBePromoted(): boolean {
        return ( this.jobsOfferToPromote && this.jobsOfferToPromote.length > 0 ) && ( this.candidates && this.candidates.length > 0 );
    }

    back() {
        window.history.back();
    }

    trackId( index: number, item: any ) {
        return item.id;
    }
}
