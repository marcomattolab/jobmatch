import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core';
import { JhiDataUtils, JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';
import { SponsoringInstitutionService } from './sponsoring-institution.service';
import { FileService } from 'app/shared/file/file.service';
import { MAX_SIZE_PROFILE_IMAGE } from 'app/shared/constants/input.constants';
import { ModalService } from 'app/shared/modal/modal.service';
import { SponsoringInstitutionUpdateAnagraficaDialogComponent } from './sponsoring-institution-update-anagrafica-dialog.component';
import { IJobOffer, JobOfferStatus } from 'app/shared/model/job-offer.model';
import { CompanyInfo } from 'app/shared/model/company.model';
import { JobOfferService } from '../job-offer/job-offer.service';
import { CompanyService } from '../company/company.service';
import { pipeToResponse } from 'app/shared/util/request-util';

@Component( {
    selector: 'jhi-sponsoring-institution-profile',
    templateUrl: './sponsoring-institution-profile.component.html'
} )
export class SponsoringInstitutionProfileComponent implements OnInit, OnDestroy {
    sponsoringInstitution: ISponsoringInstitution;
    company: CompanyInfo;
    jobOffers: IJobOffer[];
    eventSubscriber: Subscription;
    showBackBtn: boolean;

    constructor( protected sponsoringInstitutionService: SponsoringInstitutionService,
        protected dataUtils: JhiDataUtils,
        protected activatedRoute: ActivatedRoute,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected fileService: FileService,
        protected accountService: AccountService,
        protected modalService: ModalService,
        protected jobOfferService: JobOfferService,
        protected companyService: CompanyService ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { sponsoringInstitution, showBackBtn } ) => {
            this.sponsoringInstitution = sponsoringInstitution;
            pipeToResponse( this.companyService.findCompanyInfoBySponsoringInstitution( this.sponsoringInstitution.id ) ).subscribe(
                ( res: CompanyInfo ) => {
                    this.company = res;
                    this.reloadJobsOffer();
                } );
            this.showBackBtn = showBackBtn;

            this.eventSubscriber = this.eventManager.subscribe( 'sponsoringInstitutionModified', response => this.reloadSponsoringInstitution() );
        } );
    }

    ngOnDestroy(): void {
        this.eventManager.destroy( this.eventSubscriber );
    }

    reloadSponsoringInstitution() {
        this.sponsoringInstitutionService.find( this.sponsoringInstitution.id ).subscribe(
            ( res: HttpResponse<ISponsoringInstitution> ) => {
                this.sponsoringInstitution = res.body;
            }
        );
    }

    reloadJobsOffer() {
        const params = {
            'companyId.equals': this.company.id,
            'status.equals': JobOfferStatus.ACTIVE,
            sort: ['startDate' + ',' + ( 'desc' )]
        };
        pipeToResponse( this.jobOfferService.query( params ) ).subscribe(
            ( res: IJobOffer[] ) => this.jobOffers = res
        );
    }

    onUpdateAnagrafica() {
        const params = { sponsoringInstitution: Object.assign( {}, this.sponsoringInstitution ) };
        this.modalService.openLargeModal( SponsoringInstitutionUpdateAnagraficaDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    selectFile() {
        document.getElementById( 'inputPicture' ).click();
    }

    processFile( imageInput: any ) {
        const file: File = imageInput.files[0];
        if ( file.size > MAX_SIZE_PROFILE_IMAGE ) {
            alert( 'L\'immagine non può superare i 2MB' );
            return;
        }

        const reader = new FileReader();

        reader.addEventListener( 'load', ( event: any ) => {
            this.fileService.uploadUserProfileImg( file ).subscribe(
                ( res: HttpResponse<any> ) => {
                    this.sponsoringInstitution.imageUrl = res.body.fileName;
                    this.accountService.identity( true );
                },
                ( err: HttpErrorResponse ) => {
                    this.jhiAlertService.error( err.message, null, null );
                } );
        } );
        reader.readAsDataURL( file );
    }

    trackById( index: number, item: any ) {
        return item.id;
    }

    previousState() {
        window.history.back();
    }
}
