import { PlatformLocation } from '@angular/common';
import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IJobOffer, JobOfferStatus, ChangeJobStateRequest } from 'app/shared/model/job-offer.model';
import { JhiEventManager } from 'ng-jhipster';
import { JobOfferService } from '../job-offer/job-offer.service';
import { TranslateService } from '@ngx-translate/core';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';

@Component( {
    selector: 'jhi-company-change-state-job-offer-dialog',
    templateUrl: './company-change-state-job-offer-dialog.component.html'
} )
export class CompanyChangeStateJobOfferDialogComponent {
    jobOffer: IJobOffer;
    status: JobOfferStatus;

    constructor( protected jobOfferService: JobOfferService, public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager, private location: PlatformLocation, protected translateService: TranslateService, protected userSessionStorageService: UserSessionStorageService ) {
        location.onPopState(() => this.activeModal.dismiss() );
    }

    clear() {
        this.activeModal.dismiss( 'cancel' );
    }

    confirm( id: number ) {
        this.jobOfferService.changeJobStatus( new ChangeJobStateRequest( this.jobOffer.id, this.status ) ).subscribe( response => {
            this.eventManager.broadcast( {
                name: 'jobOfferModified',
                content: 'Change status of an jobOffer'
            } );
            this.activeModal.dismiss( true );
            this.userSessionStorageService.clear('countActiveJobOffers');
            this.userSessionStorageService.clear('countAllActiveJobOffers');
        } );
    }

    getStatusDescription() {
        return this.translateService.instant( 'jobmatchApp.JobOfferStatus.' + this.status );
    }
}
