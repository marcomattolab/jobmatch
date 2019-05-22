import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppliedJob } from 'app/shared/model/applied-job.model';
import { AppliedJobService } from './applied-job.service';

@Component({
    selector: 'jhi-applied-job-delete-dialog',
    templateUrl: './applied-job-delete-dialog.component.html'
})
export class AppliedJobDeleteDialogComponent {
    appliedJob: IAppliedJob;

    constructor(
        protected appliedJobService: AppliedJobService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appliedJobService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appliedJobListModification',
                content: 'Deleted an appliedJob'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-applied-job-delete-popup',
    template: ''
})
export class AppliedJobDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appliedJob }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AppliedJobDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.appliedJob = appliedJob;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/applied-job', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/applied-job', { outlets: { popup: null } }]);
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
