import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from '../document/document.service';

@Component({
    selector: 'jhi-document-delete-dialog',
    templateUrl: './candidate-delete-document-dialog.component.html'
})
export class CandidateDeleteDocumentDialogComponent {
    document: IDocument;

    constructor(
        protected documentService: DocumentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentsModified',
                content: 'Deleted an Document'
            });
            this.activeModal.dismiss(true);
        });
    }
}

/*
@Component({
    selector: 'jhi-document-delete-popup',
    template: ''
})
export class CandidateDeleteDocumentPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ document }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CandidateDeleteDocumentDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.document = document;
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
