import { PlatformLocation } from '@angular/common';
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IJobOffer } from 'app/shared/model/job-offer.model';
import { JhiEventManager } from 'ng-jhipster';
import { JobOfferService } from '../job-offer/job-offer.service';

@Component({
    selector: 'jhi-company-delete-job-offer-dialog',
    templateUrl: './company-delete-job-offer-dialog.component.html'
})
export class CompanyDeleteJobOfferDialogComponent {
    jobOffer: IJobOffer;

    constructor(protected jobOfferService: JobOfferService, public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager, private location: PlatformLocation) {
        location.onPopState(() => this.activeModal.dismiss());
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobOfferService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'jobOfferDeleted',
                content: 'Deleted an jobOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

/*@Component({
    selector: 'jhi-company-delete-job-offer-popup',
    template: ''
})
export class CompanyDeleteJobOfferPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobOffer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompanyDeleteJobOfferDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.jobOffer = jobOffer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/company', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/company', { outlets: { popup: null } }]);
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
