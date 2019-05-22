import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map, tap } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAppliedJob, ApplyToJobRequest, AppliedJobItem } from 'app/shared/model/applied-job.model';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';

type EntityResponseType = HttpResponse<IAppliedJob>;
type EntityArrayResponseType = HttpResponse<IAppliedJob[]>;
type EntityItemArrayResponseType = HttpResponse<AppliedJobItem[]>;
type EntityCountResponseType = HttpResponse<number>;

@Injectable( { providedIn: 'root' } )
export class AppliedJobService {
    public resourceUrl = SERVER_API_URL + 'api/applied-jobs';

    constructor( protected http: HttpClient, protected userSessionStorageService: UserSessionStorageService ) { }

    create( appliedJob: IAppliedJob ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( appliedJob );
        return this.http
            .post<IAppliedJob>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( appliedJob: IAppliedJob ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( appliedJob );
        return this.http
            .put<IAppliedJob>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    applyToJob( applyToJobRequest: ApplyToJobRequest ): Observable<EntityResponseType> {
        return this.http
            .put<IAppliedJob>( this.resourceUrl + '/applyToJobOffer', applyToJobRequest, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

//    applyToCompany( query: { candidateId: number, companyId: number } ): Observable<EntityResponseType> {
//        return this.http
//            .put<IAppliedJob>( this.resourceUrl + '/applyToCompany', query, { observe: 'response' } );
//    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<IAppliedJob>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<IAppliedJob[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    queryItems( req?: any ): Observable<EntityItemArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<AppliedJobItem[]>( this.resourceUrl + '/applyments', { params: options, observe: 'response' } )
            .pipe( map(( res: EntityItemArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    count( req?: any ): Observable<EntityCountResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<number>( this.resourceUrl + '/count', { params: options, observe: 'response' } );
    }

    countAllApplyments( req?: any ): Observable<number> {
        const options = createRequestOption( req );
        const countCached = this.userSessionStorageService.retrieve( 'countAllApplyments' );
        if ( countCached != null ) {
            return of( countCached );
        }
        return this.http
            .get<number>( this.resourceUrl + `/count`, { params: options, observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( res: number ) => this.userSessionStorageService.store( 'countAllApplyments', res ) )
            );
    }

    countNewApplications( companyId: number ): Observable<number> {
        const newAppliedJobCount = this.userSessionStorageService.retrieve( 'countNewApplications' );
        if ( newAppliedJobCount != null ) {
            return of( newAppliedJobCount );
        }
        return this.http
            .get<number>( this.resourceUrl + `/newAppliedJobCount/${companyId}`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( res: number ) => this.userSessionStorageService.store( 'countNewApplications', res ) )
            );
    }

    countApplymentsCandidate( candidateId: number ): Observable<number> {
        const countApplymentsCandidate = this.userSessionStorageService.retrieve( 'countApplymentsCandidate' );
        if ( countApplymentsCandidate != null ) {
            return of( countApplymentsCandidate );
        }
        return this.http
            .get<number>( this.resourceUrl + `/newAppliedJobCountCandidate/${candidateId}`, { observe: 'response' } ).pipe(
            map(( res: EntityCountResponseType ) => res.body ),
            tap(( res: number ) => this.userSessionStorageService.store( 'countApplymentsCandidate', res ) )
            );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( appliedJob: IAppliedJob ): IAppliedJob {
        const copy: IAppliedJob = Object.assign( {}, appliedJob, {
            createdDate: appliedJob.createdDate != null && appliedJob.createdDate.isValid() ? appliedJob.createdDate.toJSON() : null,
            lastModifiedDate:
            appliedJob.lastModifiedDate != null && appliedJob.lastModifiedDate.isValid() ? appliedJob.lastModifiedDate.toJSON() : null
        } );
        return copy;
    }

    protected convertDateFromServer( res: EntityResponseType ): EntityResponseType {
        if ( res.body ) {
            res.body.createdDate = res.body.createdDate != null ? moment( res.body.createdDate ) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment( res.body.lastModifiedDate ) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer( res: EntityArrayResponseType ): EntityArrayResponseType {
        if ( res.body ) {
            res.body.forEach(( appliedJob: IAppliedJob ) => {
                appliedJob.createdDate = appliedJob.createdDate != null ? moment( appliedJob.createdDate ) : null;
                appliedJob.lastModifiedDate = appliedJob.lastModifiedDate != null ? moment( appliedJob.lastModifiedDate ) : null;
            } );
        }
        return res;
    }
}
