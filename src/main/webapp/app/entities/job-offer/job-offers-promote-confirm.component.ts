import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PlatformLocation } from '@angular/common';
import { PromoteJobOfferCustomMessage, PromoteJobOffer } from 'app/shared/model/job-offer.model';
import { JobOfferService } from './job-offer.service';
import { HttpResponse } from '@angular/common/http';

@Component( {
    selector: 'jhi-job-offers-promote-confirm-component',
    templateUrl: './job-offers-promote-confirm.component.html'
} )
export class JobOffersPromoteConfirmComponent implements OnInit {
    title: string;
    candidatesId: number[];
    jobOffersId: number[];
    customMessages: PromoteJobOfferCustomMessage[];

    isPromoting: boolean;

    constructor(
        private jobOfferService: JobOfferService,
        private activeModal: NgbActiveModal,
        private location: PlatformLocation ) {

        location.onPopState(() => this.activeModal.dismiss() );
    }

    ngOnInit(): void {
        this.isPromoting = false;
    }

    clear() {
        this.activeModal.dismiss();
    }

    confirm() {
        this.isPromoting = true;

        for ( const currMess of this.customMessages ) {
            if ( !currMess.customMessage || currMess.customMessage.trim().length === 0 ) {
                this.isPromoting = false;
                return;
            }
        }

        this.jobOfferService.promoteJobOffers( new PromoteJobOffer( this.jobOffersId, this.candidatesId, this.customMessages ) ).subscribe(
            ( res: HttpResponse<any> ) => {
                this.activeModal.close( true );
            },
            err => {

            },
            () => this.isPromoting = false
        );
    }
}
