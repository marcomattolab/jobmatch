import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { AccountService } from 'app/core';
import { FileService } from 'app/shared/file/file.service';
import { ICompany } from 'app/shared/model/company.model';
import { IJobOffer, JobOfferStatus } from 'app/shared/model/job-offer.model';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { AppliedJobService } from '../applied-job';
import { DirectApplicationService } from '../direct-application';
import { JobOfferService } from '../job-offer/job-offer.service';
import { CompanyService } from './company.service';
import { ICompanySkill, SkillLevelType, CompanySkill } from 'app/shared/model/company-skill.model';
import { CompanySkillService } from '../company-skill';
import { pipeToResponse } from 'app/shared/util/request-util';
import { MAX_SIZE_PROFILE_IMAGE } from 'app/shared/constants/input.constants';
import { ModalService } from 'app/shared/modal/modal.service';
import { CompanyUpdateSkillDialogComponent } from './company-update-skill-dialog.component';
import { SkillTag, SkillType } from 'app/shared/model/skill-tag.model';
import { CompanyUpdateAnagraficaDialogComponent } from './company-update-anagrafica-dialog.component';
import { CompanyDeleteSkillDialogComponent } from './company-delete-skill-dialog.component';
import { enableFooter, disableFooter } from 'app/shared';

@Component( {
    selector: 'jhi-company-profile',
    templateUrl: './company-profile.component.html'
} )
export class CompanyProfileComponent implements OnInit, OnDestroy {
    company: ICompany;
    jobOffers: IJobOffer[];
    otherSkills: ICompanySkill[];
    companyEventSubscriber: Subscription;
    companySkillsEventSubscriber: Subscription;
    alreadyApplied: boolean;
    skillLevel = Object.keys( SkillLevelType );
    showBackBtn: boolean;

    constructor( protected companyService: CompanyService,
        protected jobOfferService: JobOfferService,
        protected dataUtils: JhiDataUtils,
        protected activatedRoute: ActivatedRoute,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected fileService: FileService,
        protected jhiAlertService: JhiAlertService,
        protected companySkillService: CompanySkillService,
        protected appliedJobService: AppliedJobService,
        protected directApplicationService: DirectApplicationService,
        protected modalService: ModalService ) {

    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { company, showBackBtn } ) => {
            this.company = company;
            this.showBackBtn = showBackBtn;
            this.companyEventSubscriber = this.eventManager.subscribe( 'companyModified', response => this.reloadCompany() );
            this.companySkillsEventSubscriber = this.eventManager.subscribe( 'companySkillsModified', response => this.reloadSkills() );
            this.reloadJobsOffer();
            this.reloadSkills();
            this.reloadIfAlreadyApplied();
        } );

        enableFooter(document);
    }

    ngOnDestroy(): void {
        this.eventManager.destroy( this.companyEventSubscriber );
        this.eventManager.destroy( this.companySkillsEventSubscriber );
        disableFooter(document);
    }

    reloadCompany() {
        pipeToResponse( this.companyService.find( this.company.id ) ).subscribe(
            ( res: ICompany ) => this.company = res
        );
    }

    reloadIfAlreadyApplied() {
        if ( this.isCurrentRoleCandidate() ) {
            this.directApplicationService.isAlreadyApplied( this.accountService.getCurrentRoleId(), this.company.id ).subscribe(
                ( res: boolean ) => this.alreadyApplied = res
            );
        }
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
                    this.company.imageUrl = res.body.fileName;
                    this.accountService.identity( true );
                },
                ( err: HttpErrorResponse ) => {
                    this.jhiAlertService.error( err.message, null, null );
                } );
        } );
        reader.readAsDataURL( file );
    }

    onAnagraficaUpdate() {
        const params = { company: Object.assign( {}, this.company ) };
        this.modalService.openLargeModal( CompanyUpdateAnagraficaDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onUpdateSkill( companySkill: ICompanySkill ) {
        const params = {
            currentSkill: companySkill ? Object.assign( {}, companySkill ) : new CompanySkill(),
            companyId: this.company.id,
            skillType: SkillType.OTHER
        };

        this.modalService.openLargeModal( CompanyUpdateSkillDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onDeleteSkill( companySkill: ICompanySkill ) {
        if ( !companySkill ) { return; }

        const params = {
            skill: companySkill
        };

        this.modalService.openLargeModal( CompanyDeleteSkillDialogComponent, params )
            .then( res => {

            } ).catch( err => {

            } );
    }

    onCandidateCompany() {
        this.directApplicationService.create( { candidateId: this.accountService.getCurrentRoleId(), companyId: this.company.id } ).subscribe(
            res => this.alreadyApplied = true,
            err => this.alreadyApplied = false
        );
    }

    previousState() {
        window.history.back();
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

    reloadSkills() {
        const params = {
            'companyId.equals': this.company.id,
            sort: ['createdDate' + ',' + ( 'desc' )]
        };
        pipeToResponse( this.companySkillService.query( params ) ).subscribe(
            ( res: ICompanySkill[] ) => {
                this.sortSkills( res );
                this.otherSkills = res;
            }
        );
    }

    sortSkills( skills: ICompanySkill[] ) {
        skills.sort(( a: ICompanySkill, b: ICompanySkill ) => {
            if ( this.skillLevel.indexOf( a.level ) < this.skillLevel.indexOf( b.level ) ) {
                return 1;
            }
            if ( this.skillLevel.indexOf( a.level ) > this.skillLevel.indexOf( b.level ) ) {
                return -1;
            } else {
                return 0;
            }
        } );
    }

    trackById( index: number, item: any ) {
        return item.id;
    }

    isCurrentRoleCandidate(): boolean {
        return this.accountService.isCurrentRoleCandidate();
    }
}
