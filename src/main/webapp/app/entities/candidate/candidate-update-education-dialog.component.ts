import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EducationType, IEducation } from 'app/shared/model/education.model';
import { JhiEventManager } from 'ng-jhipster';
import { EducationService } from '../education/education.service';

@Component( {
    selector: 'jhi-candidate-update-education-dialog',
    templateUrl: './candidate-update-education-dialog.component.html'
} )
export class CandidateUpdateEducationDialogComponent {
    candidateId: number;
    currentEducation: IEducation;
    educationType: EducationType;
    invalidDates = false;
    needReload = true;
    isSaving = false;

    constructor(
        protected educationService: EducationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) { }

    isEducationType(): boolean {
        return this.currentEducation.educationType === EducationType.EDUCATION || this.educationType === EducationType.EDUCATION;
    }

    isCertificationType(): boolean {
        return this.currentEducation.educationType === EducationType.CERTIFICATION || this.educationType === EducationType.CERTIFICATION;
    }

    confirm() {
        this.isSaving = true;
        if ( ( this.isEducationType() && this.currentEducation.current )
            || ( this.isCertificationType() && !this.currentEducation.expires ) ) {
            this.currentEducation.endDate = null;
        } else {
            this.invalidDates = !this.currentEducation.startDate || !this.currentEducation.endDate
                || this.currentEducation.startDate.isAfter( this.currentEducation.endDate );
            if ( this.invalidDates ) {
                this.isSaving = false;
                return;
            }
        }

        if ( this.currentEducation.id ) {
            this.educationService.update( this.currentEducation ).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        } else {
            this.currentEducation.candidateId = this.candidateId;
            this.currentEducation.educationType = this.educationType;
            this.educationService.create( this.currentEducation ).subscribe(
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

    uniqueTranslateLabel() {
        return this.isCertificationType() ? 'certification' : 'education';
    }

    onClose() {
        if ( this.needReload ) {
            this.eventManager.broadcast( {
                name: 'educationModified',
                content: 'Candidate s educations has been modified'
            } );
            this.activeModal.dismiss( true );
        }
    }

    onSelectCurrent( event: any ) {
        if ( this.currentEducation.current ) {
            this.currentEducation.endDate = null;
        }
    }

    onSelectExpires( event: any ) {
        if ( !this.currentEducation.expires ) {
            this.currentEducation.endDate = null;
        }
    }
}

/*
@Component( {
    selector: 'jhi-candidate-update-education-popup',
    template: ''
} )
export class CandidateUpdateEducationPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor( protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { education } ) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open( CandidateUpdateEducationDialogComponent as Component, { size: 'lg', backdrop: 'static' } );
                this.ngbModalRef.componentInstance.currentEducation = education;
                this.ngbModalRef.componentInstance.candidateId = this.activatedRoute.snapshot.params['id'];
                this.ngbModalRef.componentInstance.educationType = this.activatedRoute.snapshot.data['educationType'];

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
