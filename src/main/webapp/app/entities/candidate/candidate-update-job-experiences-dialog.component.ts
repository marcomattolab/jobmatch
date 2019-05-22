import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IJobExperience } from 'app/shared/model/job-experience.model';
import { JhiEventManager } from 'ng-jhipster';
import { JobExperienceService } from '../job-experience/job-experience.service';
import { Country } from 'app/shared/model/candidate.model';

@Component( {
    selector: 'jhi-candidate-update-job-experiences-dialog',
    templateUrl: './candidate-update-job-experiences-dialog.component.html'
} )
export class CandidateUpdateJobExperiencesDialogComponent {
    candidateId: number;
    currentJobExperience: IJobExperience;
    needReload = true;
    invalidDates = false;
    isSaving = false;
    countries = Object.keys( Country );

    constructor(
        protected jobExperienceService: JobExperienceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) { }

    confirm() {
        this.isSaving = true;
        if ( this.currentJobExperience.current ) {
            this.currentJobExperience.endDate = null;
        } else {
            this.invalidDates = !this.currentJobExperience.startDate || !this.currentJobExperience.endDate || this.currentJobExperience.startDate.isAfter(this.currentJobExperience.endDate);
            if (this.invalidDates) {
                this.isSaving = false;
                return;
            }
        }
        if ( this.currentJobExperience.id ) {
            this.jobExperienceService.update( this.currentJobExperience ).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        } else {
            this.currentJobExperience.candidateId = this.candidateId;
            this.jobExperienceService.create( this.currentJobExperience ).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        }
    }

    onClose() {
        if ( this.needReload ) {
            this.eventManager.broadcast( {
                name: 'jobExperiencesModified',
                content: 'Candidate s job-experiences has been modified'
            } );
            this.activeModal.dismiss( true );
        }
    }

    onSelectCurrent( event: any ) {
        if ( this.currentJobExperience.current ) {
            this.currentJobExperience.endDate = null;
        }
    }
}

/*
@Component( {
    selector: 'jhi-candidate-update-job-experiences-popup',
    template: ''
} )
export class CandidateUpdateJobExperiencesPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor( protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { jobExperience } ) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open( CandidateUpdateJobExperiencesDialogComponent as Component, { size: 'lg', backdrop: 'static' } );
                this.ngbModalRef.componentInstance.currentJobExperience = jobExperience;
                this.ngbModalRef.componentInstance.candidateId = this.activatedRoute.snapshot.params['id'];

                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate( ['/candidate', { outlets: { popup: null } }] );
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate( ['/candidate', { outlets: { popup: null } }] );
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
