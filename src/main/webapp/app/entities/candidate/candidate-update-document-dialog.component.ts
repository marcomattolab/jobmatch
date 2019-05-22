import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IDocument, DocumentType } from 'app/shared/model/document.model';
import { JhiEventManager } from 'ng-jhipster';
import { DocumentService } from '../document/document.service';
import { isNullOrUndefined } from 'util';

@Component({
    selector: 'jhi-candidate-update-document-dialog',
    templateUrl: './candidate-update-document-dialog.component.html'
})
export class CandidateUpdateDocumentDialogComponent {
    acceptedExtensions = [
        { ext: 'pdf', contentType: 'application/pdf' },
        { ext: 'doc', contentType: 'application/msword' },
        { ext: 'docx', contentType: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' }
    ];
    maxSizeDocument = 2 * 1024 * 1024;
    candidateId: number;
    currentDocument: IDocument;
    documentType: DocumentType;
    contentType: any;
    needReload = true;
    isSaving = false;
    wrongExtension = false;
    sizeExceeded = false;

    constructor(
        protected documentService: DocumentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) { }

    confirm() {
        this.isSaving = true;
        if (this.wrongExtension || this.sizeExceeded) {
            return;
        }

        this.currentDocument.documentType = this.documentType;
        this.createOrUpdateDocument();
    }

    createOrUpdateDocument() {
        if (this.currentDocument.id) {
            this.documentService.update(this.currentDocument).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        } else {
            this.currentDocument.candidateId = this.candidateId;
            this.documentService.create(this.currentDocument).subscribe(
                res => {
                    this.needReload = true;
                    this.onClose();
                },
                err => {
                    this.isSaving = false;
                }
            );
        }
    }

    selectFile() {
        document.getElementById('file_content').click();
    }

    processFile(imageInput: any) {
        const file: File = imageInput.files[0];
        const nameSplitted = file.name.split('.');
        const extension = (nameSplitted && nameSplitted.length) > 0 ? nameSplitted[nameSplitted.length - 1] : null;
        this.wrongExtension = isNullOrUndefined(this.contentType = this.acceptedExtensions.find(exts => exts.ext === extension));

        this.sizeExceeded = file.size >= this.maxSizeDocument;
        if (this.sizeExceeded || this.wrongExtension) {
            return;
        }

        const reader = new FileReader();
        reader.addEventListener('load', (event: any) => {
            const contentToString = reader.result as string;
            this.currentDocument.content = contentToString.substr(contentToString.indexOf('base64,') + 'base64,'.length);
            this.currentDocument.contentContentType = this.contentType.contentType;
        });
        reader.readAsDataURL(file);
    }

    onClose() {
        if (this.needReload) {
            this.eventManager.broadcast({
                name: 'documentsModified',
                content: 'Candidate s documents has been modified'
            });
            this.activeModal.dismiss(true);
        }
    }
}

/*
@Component({
    selector: 'jhi-candidate-update-document-popup',
    template: ''
})
export class CandidateUpdateDocumentPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router,
        protected modalService: NgbModal) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ document }) => {
            setTimeout(() => {
                console.log('document', document);
                this.ngbModalRef = this.modalService.open(CandidateUpdateDocumentDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.currentDocument = document;
                this.ngbModalRef.componentInstance.candidateId = this.activatedRoute.snapshot.params['id'];
                this.ngbModalRef.componentInstance.documentType = this.activatedRoute.snapshot.data['documentType'];

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
