import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiDataUtils } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { CompanyService } from '../company/company.service';
import { JobOfferService } from '../job-offer/job-offer.service';
import { CompanySectorService } from '../company-sector/company-sector.service';
import { IProject } from 'app/shared/model/project.model';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanyInfo } from 'app/shared/model/company.model';
import { IJobOffer, JobOfferStatus } from 'app/shared/model/job-offer.model';

@Component( {
    selector: 'jhi-project-detail',
    templateUrl: './project-detail.component.html'
} )
export class ProjectDetailComponent implements OnInit {
    company: CompanyInfo;
    project: IProject;
    sector: ICompanySector;
    jobOffers: IJobOffer[];

    constructor( protected dataUtils: JhiDataUtils,
        protected activatedRoute: ActivatedRoute,
        protected companySectorService: CompanySectorService,
        protected companyService: CompanyService,
        protected accountService: AccountService,
        protected jobOfferService: JobOfferService ) { }

    ngOnInit() {
        this.activatedRoute.data.subscribe(( { project } ) => {
            this.project = project;
            this.loadSector();
            this.loadCompanyInfo();
            this.loadJobsOffer();
        } );
    }

    byteSize( field ) {
        return this.dataUtils.byteSize( field );
    }

    openFile( contentType, field ) {
        return this.dataUtils.openFile( contentType, field );
    }
    previousState() {
        window.history.back();
    }

    loadSector() {
        if ( this.project.sectorId ) {
            this.companySectorService.find( this.project.sectorId ).subscribe(
                ( res: HttpResponse<ICompanySector> ) => this.sector = res.body
            );
        }
    }

    loadCompanyInfo() {
        if ( this.project.companyId ) {
            this.companyService.findInfo( this.project.companyId ).subscribe(
                ( res: HttpResponse<CompanyInfo> ) => this.company = res.body
            );
        }
    }

    loadJobsOffer() {
        const params = {
            'projectId.equals': this.project.id,
            sort: ['startDate' + ',' + ( 'desc' )]
        };
        this.jobOfferService.query( params ).subscribe(
            ( res: HttpResponse<IJobOffer[]> ) => this.jobOffers = res.body
        );
    }

    trackById( index: number, item: any ) {
        return item.id;
    }
}
