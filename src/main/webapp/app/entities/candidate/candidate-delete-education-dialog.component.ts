import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEducation } from 'app/shared/model/education.model';
import { EducationService } from '../education/education.service';

@Component({
    selector: 'jhi-education-delete-dialog',
    templateUrl: './candidate-delete-education-dialog.component.html'
})
export class CandidateDeleteEducationDialogComponent {
    education: IEducation;

    constructor(
        protected educationService: EducationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.educationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'educationModified',
                content: 'Deleted an education'
            });
            this.activeModal.dismiss(true);
        });
    }
}

/*
@Component({
    selector: 'jhi-education-delete-popup',
    template: ''
})
export class CandidateDeleteEducationPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ education }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CandidateDeleteEducationDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.education = education;
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
