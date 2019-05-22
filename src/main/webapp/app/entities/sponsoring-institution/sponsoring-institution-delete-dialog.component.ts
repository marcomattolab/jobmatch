import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';
import { SponsoringInstitutionService } from './sponsoring-institution.service';

@Component({
    selector: 'jhi-sponsoring-institution-delete-dialog',
    templateUrl: './sponsoring-institution-delete-dialog.component.html'
})
export class SponsoringInstitutionDeleteDialogComponent {
    sponsoringInstitution: ISponsoringInstitution;

    constructor(
        protected sponsoringInstitutionService: SponsoringInstitutionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sponsoringInstitutionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sponsoringInstitutionListModification',
                content: 'Deleted an sponsoringInstitution'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sponsoring-institution-delete-popup',
    template: ''
})
export class SponsoringInstitutionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sponsoringInstitution }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SponsoringInstitutionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.sponsoringInstitution = sponsoringInstitution;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sponsoring-institution', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sponsoring-institution', { outlets: { popup: null } }]);
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
