import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';
import { JobOfferSkillService } from './job-offer-skill.service';

@Component({
    selector: 'jhi-job-offer-skill-delete-dialog',
    templateUrl: './job-offer-skill-delete-dialog.component.html'
})
export class JobOfferSkillDeleteDialogComponent {
    jobOfferSkill: IJobOfferSkill;

    constructor(
        protected jobOfferSkillService: JobOfferSkillService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobOfferSkillService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'jobOfferSkillListModification',
                content: 'Deleted an jobOfferSkill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-offer-skill-delete-popup',
    template: ''
})
export class JobOfferSkillDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobOfferSkill }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JobOfferSkillDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.jobOfferSkill = jobOfferSkill;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/job-offer-skill', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/job-offer-skill', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
