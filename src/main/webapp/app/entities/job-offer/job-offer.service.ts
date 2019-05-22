import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map, tap } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobOffer, ChangeJobStateRequest, PromoteJobOffer, PromoteJobOfferCustomMessage } from 'app/shared/model/job-offer.model';
import { Helper } from 'app/shared/util/helper-util';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { FileService } from 'app/shared/file/file.service';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';
type EntityResponseType = HttpResponse<IJobOffer>;
type EntityArrayResponseType = HttpResponse<IJobOffer[]>;
type EntityCountResponseType = HttpResponse<number>;

@Injectable( { providedIn: 'root' } )
export class JobOfferService {
    public resourceUrl = SERVER_API_URL + 'api/job-offers';

    constructor(
        protected http: HttpClient,
        protected helper: Helper,
        protected fileService: FileService,
        protected companySectorService: CompanySectorService,
        private userSessionStorage: UserSessionStorageService ) { }

    create( jobOffer: IJobOffer ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( jobOffer );
        return this.http
            .post<IJobOffer>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( jobOffer: IJobOffer ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( jobOffer );
        return this.http
            .put<IJobOffer>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<IJobOffer>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<IJobOffer[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    findSuggestedJobOffers( candidateId: number ): Observable<EntityArrayResponseType> {
        const suggestedJobOffers = this.userSessionStorage.retrieve( 'suggestedJobOffers' );
        if ( suggestedJobOffers ) {
            return of( suggestedJobOffers );
        }
        return this.http
            .get<IJobOffer[]>( `${this.resourceUrl}/suggested/${candidateId}`, { observe: 'response' } )
            .pipe(
            map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ),
            tap(( res: EntityArrayResponseType ) => this.userSessionStorage.store( 'suggestedJobOffers', res ) )
            );
    }

    countNewJobOffers( companyId: number ): Observable<number> {
        const newJobOfferCount = this.userSessionStorage.retrieve( 'countNewJobOffers' );
        if ( newJobOfferCount != null ) {
            return of( newJobOfferCount );
        }
        return this.http
            .get<number>( this.resourceUrl + `/newJobOfferCount/${companyId}`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( count: number ) => this.userSessionStorage.store( 'countNewJobOffers', count ) )
            );
    }

    countAllActiveJobOffersByCompany( companyId: number ): Observable<number> {
        const newJobOfferCount = this.userSessionStorage.retrieve( 'countActiveJobOffers' );
        if ( newJobOfferCount != null ) {
            return of( newJobOfferCount );
        }
        return this.http
            .get<number>( this.resourceUrl + `/activeJobOffersCount/${companyId}`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( count: number ) => this.userSessionStorage.store( 'countActiveJobOffers', count ) )
            );
    }

    countAllActiveJobOffers(): Observable<number> {
        const allActiveJobOfferCount = this.userSessionStorage.retrieve( 'countAllActiveJobOffers' );
        if ( allActiveJobOfferCount != null ) {
            return of( allActiveJobOfferCount );
        }
        return this.http
            .get<number>( this.resourceUrl + `/activeJobOffersCount`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( count: number ) => this.userSessionStorage.store( 'countAllActiveJobOffers', count ) )
            );
    }

    countAllNonDraftJobOffers(): Observable<number> {
        const nonDraftJobOffersCount = this.userSessionStorage.retrieve( 'countAllNonDraftJobOffers' );
        if ( nonDraftJobOffersCount != null ) {
            return of( nonDraftJobOffersCount );
        }
        return this.http
            .get<number>( this.resourceUrl + `/nonDraftJobOffersCount`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( count: number ) => this.userSessionStorage.store( 'countAllNonDraftJobOffers', count ) )
            );
    }

    countAllWeeklyActiveJobOffers(): Observable<number> {
        const countAllWeeklyActiveJobOffers = this.userSessionStorage.retrieve( 'countAllWeeklyActiveJobOffers' );
        if ( countAllWeeklyActiveJobOffers != null ) {
            return of( countAllWeeklyActiveJobOffers );
        }
        return this.http
            .get<number>( this.resourceUrl + `/newJobOfferCount`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( count: number ) => this.userSessionStorage.store( 'countAllWeeklyActiveJobOffers', count ) )
            );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    changeJobStatus( changeJobStateRequest: ChangeJobStateRequest ): Observable<EntityResponseType> {
        return this.http
            .put<IJobOffer>( this.resourceUrl + '/changeStatus', changeJobStateRequest, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    findAllByCompanyId( companyid: number ): Observable<EntityArrayResponseType> {
        return this.http
            .get<IJobOffer[]>( `${this.resourceUrl}/company/${companyid}`, { observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    promoteJobOffers(promoteObject: PromoteJobOffer): Observable<any> {
        return this.http.post<any>( `${this.resourceUrl}/promoteJobOffers`, promoteObject, { observe: 'response' } );
    }

    findCustomPromoteJobOfferMessages(): Observable<HttpResponse<PromoteJobOfferCustomMessage>> {
        return this.http.get<PromoteJobOfferCustomMessage>( `${this.resourceUrl}/customPromoteJobOfferMessages`, { observe: 'response' } );
    }

    protected convertDateFromClient( jobOffer: IJobOffer ): IJobOffer {
        const copy: IJobOffer = Object.assign( {}, jobOffer, {
            createdDate: jobOffer.createdDate != null && jobOffer.createdDate.isValid() ? jobOffer.createdDate.toJSON() : null,
            lastModifiedDate:
            jobOffer.lastModifiedDate != null && jobOffer.lastModifiedDate.isValid() ? jobOffer.lastModifiedDate.toJSON() : null,
            startDate: jobOffer.startDate != null && jobOffer.startDate.isValid() ? jobOffer.startDate.format( DATE_FORMAT ) : null
        } );
        return copy;
    }

    protected convertDateFromServer( res: EntityResponseType ): EntityResponseType {
        if ( res.body ) {
            res.body.createdDate = res.body.createdDate != null ? moment( res.body.createdDate ) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment( res.body.lastModifiedDate ) : null;
            res.body.startDate = res.body.startDate != null ? moment( res.body.startDate ) : null;
            if ( res.body.startDate ) {
                res.body.timePassed = this.helper.getTimePassed( res.body.startDate );
            }
            if ( res.body.sectorId ) {
                this.companySectorService.findDescriptionById( res.body.sectorId ).subscribe(( desc: string ) => res.body.sectorDescription = desc );
            }
        }
        return res;
    }

    protected convertDateArrayFromServer( res: EntityArrayResponseType ): EntityArrayResponseType {
        if ( res.body ) {
            res.body.forEach(( jobOffer: IJobOffer ) => {
                jobOffer.createdDate = jobOffer.createdDate != null ? moment( jobOffer.createdDate ) : null;
                jobOffer.lastModifiedDate = jobOffer.lastModifiedDate != null ? moment( jobOffer.lastModifiedDate ) : null;
                jobOffer.startDate = jobOffer.startDate != null ? moment( jobOffer.startDate ) : null;
                if ( jobOffer.startDate ) {
                    jobOffer.timePassed = this.helper.getTimePassed( jobOffer.startDate );
                }
            } );
        }
        return res;
    }
}
