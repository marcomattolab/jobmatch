import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';
import { AppliedJobIterationService } from './applied-job-iteration.service';

@Component({
    selector: 'jhi-applied-job-iteration-delete-dialog',
    templateUrl: './applied-job-iteration-delete-dialog.component.html'
})
export class AppliedJobIterationDeleteDialogComponent {
    appliedJobIteration: IAppliedJobIteration;

    constructor(
        protected appliedJobIterationService: AppliedJobIterationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.appliedJobIterationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'appliedJobIterationListModification',
                content: 'Deleted an appliedJobIteration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-applied-job-iteration-delete-popup',
    template: ''
})
export class AppliedJobIterationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appliedJobIteration }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AppliedJobIterationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.appliedJobIteration = appliedJobIteration;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/applied-job-iteration', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/applied-job-iteration', { outlets: { popup: null } }]);
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
