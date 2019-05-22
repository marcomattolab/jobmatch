import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDirectApplication } from 'app/shared/model/direct-application.model';
import { DirectApplicationService } from './direct-application.service';

@Component({
    selector: 'jhi-direct-application-delete-dialog',
    templateUrl: './direct-application-delete-dialog.component.html'
})
export class DirectApplicationDeleteDialogComponent {
    directApplication: IDirectApplication;

    constructor(
        protected directApplicationService: DirectApplicationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.directApplicationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'directApplicationListModification',
                content: 'Deleted an directApplication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-direct-application-delete-popup',
    template: ''
})
export class DirectApplicationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ directApplication }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DirectApplicationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.directApplication = directApplication;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/direct-application', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/direct-application', { outlets: { popup: null } }]);
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
