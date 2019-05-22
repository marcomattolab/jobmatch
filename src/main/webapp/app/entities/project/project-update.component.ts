import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';
import { CompanyInfo } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { AccountService } from 'app/core';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { NgbModalRef, NgbTypeahead, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription, Subject, merge } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map, tap } from 'rxjs/operators';

@Component( {
    selector: 'jhi-project-update',
    templateUrl: './project-update.component.html'
} )
export class ProjectUpdateComponent implements OnInit {
    project: IProject;
    isSaving: boolean;
    company: CompanyInfo;
    companysectors: ICompanySector[];
    createdDate: string;
    lastModifiedDate: string;
    invalidDates = false;
    startDateDp: any;
    endDateDp: any;

    @ViewChild( 'companySectorTypeahead' )
    companySectorTypeahead: NgbTypeahead;
    companySectorFocus$ = new Subject<string>();
    companySectorClick$ = new Subject<string>();
    companySectorFieldTouched = false;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected projectService: ProjectService,
        protected companySectorService: CompanySectorService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected companyService: CompanyService
    ) { }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(( { project } ) => {
            this.project = project;
            this.createdDate = this.project.createdDate != null ? this.project.createdDate.format( DATE_TIME_FORMAT ) : null;
            this.lastModifiedDate = this.project.lastModifiedDate != null ? this.project.lastModifiedDate.format( DATE_TIME_FORMAT ) : null;
            this.loadCompany();
            this.loadCompanySectors();
        } );
    }

    loadCompany() {
        if ( this.accountService.isCurrentRoleCompany() || this.accountService.isCurrentRoleSponsoringInstitution() ) {
            this.companyService.findCurrentCompanyInfo().subscribe(
                ( res: HttpResponse<CompanyInfo> ) => {
                    this.company = res.body;
                    if ( !this.project.companyId ) {
                        this.project.companyId = this.company.id;
                    }
                    if ( !this.project.sectorId ) {
                        this.project.sectorId = this.company.sectorId;
                    }
                 },
                ( err: HttpErrorResponse ) => this.onError( err.message )
            );
        }
    }

    loadCompanySectors() {
        this.companySectorService.findAll().subscribe(
            ( res: HttpResponse<ICompanySector[]> ) => {
                this.companysectors = res.body;
                if ( this.project.sectorId ) {
                    const sector = this.companysectors.find( cs => cs.id === this.project.sectorId );
                    if ( sector ) {
                        this.setCompanySector( sector );
                    }
                }
            },
            ( res: HttpErrorResponse ) => this.onError( res.message )
        );

    }

    byteSize( field ) {
        return this.dataUtils.byteSize( field );
    }

    openFile( contentType, field ) {
        return this.dataUtils.openFile( contentType, field );
    }

    setFileData( event, entity, field, isImage ) {
        this.dataUtils.setFileData( event, entity, field, isImage );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.project.endDate) {
            this.invalidDates = !this.project.startDate || !this.project.endDate || this.project.startDate.isAfter(this.project.endDate);
            if (this.invalidDates) {
                this.isSaving = false;
                return;
            }
        }

        this.project.createdDate = this.createdDate != null ? moment( this.createdDate, DATE_TIME_FORMAT ) : null;
        this.project.lastModifiedDate = this.lastModifiedDate != null ? moment( this.lastModifiedDate, DATE_TIME_FORMAT ) : null;
        if ( this.project.id !== undefined ) {
            this.subscribeToSaveResponse( this.projectService.update( this.project ) );
        } else {
            this.subscribeToSaveResponse( this.projectService.create( this.project ) );
        }
    }

    protected subscribeToSaveResponse( result: Observable<HttpResponse<IProject>> ) {
        result.subscribe(( res: HttpResponse<IProject> ) => this.onSaveSuccess(), ( res: HttpErrorResponse ) => this.onSaveError() );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError( errorMessage: string ) {
        this.jhiAlertService.error( errorMessage, null, null );
    }

    trackCompanySectorById( index: number, item: ICompanySector ) {
        return item.id;
    }

    searchCompanySector = ( text$: Observable<string> ) => {
        const debouncedText$ = text$.pipe( debounceTime( 200 ), distinctUntilChanged() );

        const clicksWithClosedPopup$ = this.companySectorClick$.pipe(
            filter(() => !this.companySectorTypeahead.isPopupOpen() ),
            tap(() => this.companySectorFieldTouched = true ) );

        const inputFocus$ = this.companySectorFocus$.pipe( tap(() => this.companySectorFieldTouched = true ) );

        return merge( debouncedText$, inputFocus$, clicksWithClosedPopup$ ).pipe(
            map( term => {
                term = term.trim();

                const companyySectorFound = this.companysectors.find( sector =>
                    this.getFormattedCompanySector( sector ) === term );

                if ( !companyySectorFound ) {
                    this.project.sectorId = null;
                }

                return ( term === '' ?
                    this.getAvailableSectors() :
                    this.getAvailableSectors()
                        .filter( v => v.toLowerCase().indexOf( term.toLowerCase() ) > -1 )
                );
            }
            )
        );
    };

    private getAvailableSectors(): Array<string> {
        return this.companysectors.map( sector => this.getFormattedCompanySector( sector ) );
    }

    private getFormattedCompanySector( sector: ICompanySector | any ): string {
        return `${sector.description}`;
    }

    onSelectCompanySector( ngbTypeaheadSelectItem: NgbTypeaheadSelectItemEvent ) {
        ngbTypeaheadSelectItem.preventDefault();
        const sectorFound = this.companysectors.find( companySector =>
            this.getFormattedCompanySector( companySector ) === ngbTypeaheadSelectItem.item );
        if ( sectorFound ) {
            this.setCompanySector( sectorFound );
        }
    }

    private setCompanySector( sectorFound ) {
        this.project.sectorId = sectorFound.id;
        this.companySectorTypeahead.writeValue( this.getFormattedCompanySector( sectorFound ) );
    }
}
