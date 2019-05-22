import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobExperience } from 'app/shared/model/job-experience.model';
import { JobExperienceService } from '../job-experience/job-experience.service';

@Component({
    selector: 'jhi-job-experience-delete-dialog',
    templateUrl: './candidate-delete-job-experience-dialog.component.html'
})
export class CandidateDeleteJobExperienceDialogComponent {
    jobExperience: IJobExperience;

    constructor(
        protected jobExperienceService: JobExperienceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobExperienceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'jobExperiencesModified',
                content: 'Deleted an jobExperience'
            });
            this.activeModal.dismiss(true);
        });
    }
}

/*
@Component({
    selector: 'jhi-job-experience-delete-popup',
    template: ''
})
export class CandidateDeleteJobExperiencePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobExperience }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CandidateDeleteJobExperienceDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.jobExperience = jobExperience;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/candidate', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/candidate', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}*/
