import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ICandidate, Country } from 'app/shared/model/candidate.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { CandidateService } from './candidate.service';

@Component( {
    selector: 'jhi-candidate-update-anagrafica-dialog',
    templateUrl: './candidate-update-anagrafica-dialog.component.html'
} )
export class CandidateUpdateAnagraficaDialogComponent {
    candidate: ICandidate;
    isSaving = false;
    countries = Object.keys( Country );

    constructor(
        protected candidateService: CandidateService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService ) { }

    clear() {
        this.activeModal.dismiss( 'cancel' );
    }

    confirmUpdate() {
        this.isSaving = true;
        if ( this.candidate.id ) {
            this.clearNullInfo();
            this.subscribeToSaveResponse( this.candidateService.update( this.candidate ) );
        } else {
            this.isSaving = false;
        }
    }

    private clearNullInfo() {
        if ( !this.candidate.phone ) {
            this.candidate.phone = null;
        }
        if ( !this.candidate.mobilePhone ) {
            this.candidate.mobilePhone = null;
        }
    }

    private subscribeToSaveResponse( result: Observable<HttpResponse<ICandidate>> ) {
        result.subscribe(( res: HttpResponse<ICandidate> ) =>
            this.onSaveSuccess(),
            () => this.isSaving = false
        );
    }

    private onSaveSuccess() {
        this.eventManager.broadcast( {
            name: 'candidateModified',
            content: 'Candidate s anagrafica has been modified'
        } );
        this.activeModal.dismiss( true );
    }
}
/*
@Component({
    selector: 'jhi-candidate-update-anagrafica-popup',
    template: ''
})
export class CandidateUpdateAnagraficaPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ candidate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CandidateUpdateAnagraficaDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.candidate = candidate;
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
