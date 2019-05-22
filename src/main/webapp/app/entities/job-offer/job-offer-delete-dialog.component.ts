import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobOffer } from 'app/shared/model/job-offer.model';
import { JobOfferService } from './job-offer.service';

@Component({
    selector: 'jhi-job-offer-delete-dialog',
    templateUrl: './job-offer-delete-dialog.component.html'
})
export class JobOfferDeleteDialogComponent {
    jobOffer: IJobOffer;

    constructor(protected jobOfferService: JobOfferService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobOfferService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'jobOfferListModification',
                content: 'Deleted an jobOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-offer-delete-popup',
    template: ''
})
export class JobOfferDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobOffer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JobOfferDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.jobOffer = jobOffer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/job-offer', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/job-offer', { outlets: { popup: null } }]);
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
