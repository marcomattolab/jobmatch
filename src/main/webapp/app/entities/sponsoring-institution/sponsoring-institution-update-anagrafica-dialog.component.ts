import { HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Country } from 'app/shared/model/candidate.model';
import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';
import { JhiEventManager } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { SponsoringInstitutionService } from '.';

@Component( {
    selector: 'jhi-sponsoring-institution-update-anagrafica-dialog',
    templateUrl: './sponsoring-institution-update-anagrafica-dialog.component.html'
} )
export class SponsoringInstitutionUpdateAnagraficaDialogComponent {
    sponsoringInstitution: ISponsoringInstitution;
    isSaving = false;
    countries = Object.keys( Country );

    constructor(
        protected sponsoringInstitutionService: SponsoringInstitutionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) { }

    clear() {
        this.activeModal.dismiss( 'cancel' );
    }

    confirmUpdate() {
        this.isSaving = true;
        if ( this.sponsoringInstitution.id ) {
            this.clearNullInfo();
            this.subscribeToSaveResponse( this.sponsoringInstitutionService.update( this.sponsoringInstitution ) );
        } else {
            this.isSaving = false;
        }
    }

    private clearNullInfo() {
        if ( !this.sponsoringInstitution.phone ) {
            this.sponsoringInstitution.phone = null;
        }
        if ( !this.sponsoringInstitution.mobilePhone ) {
            this.sponsoringInstitution.mobilePhone = null;
        }
        if ( !this.sponsoringInstitution.urlSite ) {
            this.sponsoringInstitution.urlSite = null;
        }
        if ( !this.sponsoringInstitution.vatNumber ) {
            this.sponsoringInstitution.vatNumber = null;
        }
    }

    private subscribeToSaveResponse( result: Observable<HttpResponse<ISponsoringInstitution>> ) {
        result.subscribe(( res: HttpResponse<ISponsoringInstitution> ) => this.onSaveSuccess(), () => this.isSaving = false );
    }

    private onSaveSuccess() {
        this.eventManager.broadcast( {
            name: 'sponsoringInstitutionModified',
            content: 'sponsoringInstitution s anagrafica has been modified'
        } );
        this.activeModal.dismiss( true );
    }
}

/*
@Component({
    selector: 'jhi-sponsoring-institution-update-anagrafica-popup',
    template: ''
})
export class SponsoringInstitutionUpdateAnagraficaPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sponsoringInstitution }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SponsoringInstitutionUpdateAnagraficaDialogComponent as Component, { size: 'lg', backdrop: 'static' });
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
}*/
