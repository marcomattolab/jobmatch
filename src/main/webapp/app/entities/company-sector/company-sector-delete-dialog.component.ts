import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanySectorService } from './company-sector.service';

@Component({
    selector: 'jhi-company-sector-delete-dialog',
    templateUrl: './company-sector-delete-dialog.component.html'
})
export class CompanySectorDeleteDialogComponent {
    companySector: ICompanySector;

    constructor(
        protected companySectorService: CompanySectorService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companySectorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'companySectorListModification',
                content: 'Deleted an companySector'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-sector-delete-popup',
    template: ''
})
export class CompanySectorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ companySector }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompanySectorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.companySector = companySector;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/company-sector', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/company-sector', { outlets: { popup: null } }]);
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
