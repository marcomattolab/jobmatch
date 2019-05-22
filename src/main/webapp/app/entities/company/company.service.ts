import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { createRequestOption, pipeToResponse } from 'app/shared';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FileService } from 'app/shared/file/file.service';
import { CompanyInfo, ICompany } from 'app/shared/model/company.model';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';
import * as moment from 'moment';
import { Observable, of } from 'rxjs';
import { map, tap } from 'rxjs/operators';

type EntityResponseType = HttpResponse<ICompany>;
type EntityArrayResponseType = HttpResponse<ICompany[]>;
type EntityInfoResponseType = HttpResponse<CompanyInfo>;

@Injectable( { providedIn: 'root' } )
export class CompanyService {
    public resourceUrl = SERVER_API_URL + 'api/companies';

    constructor( protected http: HttpClient,
        protected fileService: FileService,
        protected companySectorService: CompanySectorService,
        protected userSessionStorage: UserSessionStorageService ) { }

    create( company: ICompany ): Observable<EntityResponseType> {
        const toSend = this.prepareBeforeSave( company );
        return this.http
            .post<ICompany>( this.resourceUrl, toSend, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( company: ICompany ): Observable<EntityResponseType> {
        const toSend = this.prepareBeforeSave( company );
        return this.http
            .put<ICompany>( this.resourceUrl, toSend, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<ICompany>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    countAll(): Observable<number> {
        const options = createRequestOption( {} );
        const countCache = this.userSessionStorage.retrieve( 'countAllCompany' );
        if ( countCache ) {
            return of( countCache );
        }
        return pipeToResponse( this.http.get<number>( `${this.resourceUrl}/count`, { params: options, observe: 'response' } ) )
            .pipe(
            tap(( count: number ) => this.userSessionStorage.store( 'countAllCompany', count )
            )
            );
    }

    retriveUserCompanyInfo( id: number ): Observable<CompanyInfo> {
        const companyInfo = this.userSessionStorage.retrieve( 'companyInfo' + id );
        if ( companyInfo ) {
            return of( companyInfo );
        }
        return this.http
            .get<CompanyInfo>( `${this.resourceUrl}-info/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityInfoResponseType ) => this.convertInfoFromServer( res ).body ),
            tap(( info: CompanyInfo ) => this.userSessionStorage.store( 'companyInfo' + id, info ) ) );
    }

    retriveCurrentUserCompanyInfo(): Observable<CompanyInfo> {
        const companyInfo = this.userSessionStorage.retrieve( 'currentCompanyInfo' );
        if ( companyInfo ) {
            return of( companyInfo );
        }
        return this.http
            .get<CompanyInfo>( `${this.resourceUrl}-info/current`, { observe: 'response' } )
            .pipe( map(( res: EntityInfoResponseType ) => this.convertInfoFromServer( res ).body ),
            tap(( info: CompanyInfo ) => this.userSessionStorage.store( 'currentCompanyInfo', info ) ) );
    }

    findInfo( id: number ): Observable<EntityInfoResponseType> {
        return this.http
            .get<CompanyInfo>( `${this.resourceUrl}-info/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityInfoResponseType ) => this.convertInfoFromServer( res ) ) );
    }

    findCompanyInfoBySponsoringInstitution( sponsoringInstitutionid: number ): Observable<EntityInfoResponseType> {
        return this.http
            .get<CompanyInfo>( `${this.resourceUrl}-info/sponsoringInstitution/${sponsoringInstitutionid}`, { observe: 'response' } )
            .pipe( map(( res: EntityInfoResponseType ) => this.convertInfoFromServer( res ) ) );
    }

    findCurrentCompanyInfo(): Observable<EntityInfoResponseType> {
        return this.http
            .get<CompanyInfo>( `${this.resourceUrl}-info/current`, { observe: 'response' } )
            .pipe( map(( res: EntityInfoResponseType ) => this.convertInfoFromServer( res ) ) );
    }

    findSuggestedCompanies( candidateId: number ): Observable<EntityArrayResponseType> {
        const suggestedCompanies = this.userSessionStorage.retrieve( 'suggestedCompanies' );
        if ( suggestedCompanies ) {
            return of( suggestedCompanies );
        }
        return this.http
            .get<ICompany[]>( `${this.resourceUrl}/suggested/${candidateId}`, { observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ),
            tap(( res: EntityArrayResponseType ) => this.userSessionStorage.store( 'suggestedCompanies', res ) ) );
    }

    findByUser(): Observable<EntityResponseType> {
        return this.http
            .get<ICompany>( `${this.resourceUrl}/currentUser`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<ICompany[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected prepareBeforeSave( company: ICompany ): ICompany {
        const copy = this.convertDateFromClient( company );

        if ( copy.urlSite && copy.urlSite === '' ) {
            copy.urlSite = null;
        }

        return copy;
    }

    protected convertDateFromClient( company: ICompany ): ICompany {
        const copy: ICompany = Object.assign( {}, company, {
            createdDate: company.createdDate != null && company.createdDate.isValid() ? company.createdDate.toJSON() : null,
            lastModifiedDate:
            company.lastModifiedDate != null && company.lastModifiedDate.isValid() ? company.lastModifiedDate.toJSON() : null,
            foundationDate:
            company.foundationDate != null && company.foundationDate.isValid() ? company.foundationDate.format( DATE_FORMAT ) : null
        } );
        return copy;
    }

    protected convertDateFromServer( res: EntityResponseType ): EntityResponseType {
        if ( res.body ) {
            res.body.createdDate = res.body.createdDate != null ? moment( res.body.createdDate ) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment( res.body.lastModifiedDate ) : null;
            res.body.foundationDate = res.body.foundationDate != null ? moment( res.body.foundationDate ) : null;
            if ( res.body.sectorId ) {
                this.companySectorService.findDescriptionById( res.body.sectorId ).subscribe(
                    ( desc: string ) => res.body.sectorDescription = desc
                );
                //                this.companySectorService.find( res.body.sectorId ).subscribe(
                //                    ( cs: HttpResponse<ICompanySector> ) => res.body.sectorDescription = cs.body.description
                //                );
            }
        }
        return res;
    }

    protected convertDateArrayFromServer( res: EntityArrayResponseType ): EntityArrayResponseType {
        if ( res.body ) {
            res.body.forEach(( company: ICompany ) => {
                company.createdDate = company.createdDate != null ? moment( company.createdDate ) : null;
                company.lastModifiedDate = company.lastModifiedDate != null ? moment( company.lastModifiedDate ) : null;
                company.foundationDate = company.foundationDate != null ? moment( company.foundationDate ) : null;
            } );
        }
        return res;
    }

    protected convertInfoFromServer( res: EntityResponseType ): EntityResponseType {
        if ( res.body ) {
            if ( res.body.sectorId ) {
                this.companySectorService.findDescriptionById( res.body.sectorId ).subscribe(
                    ( desc: string ) => res.body.sectorDescription = desc
                );
                //                this.companySectorService.find( res.body.sectorId ).subscribe(
                //                    ( cs: HttpResponse<ICompanySector> ) => res.body.sectorDescription = cs.body.description
                //                );
            }
        }
        return res;
    }

}
