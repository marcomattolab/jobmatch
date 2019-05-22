import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobExperience } from 'app/shared/model/job-experience.model';

type EntityResponseType = HttpResponse<IJobExperience>;
type EntityArrayResponseType = HttpResponse<IJobExperience[]>;

@Injectable( { providedIn: 'root' } )
export class JobExperienceService {
    public resourceUrl = SERVER_API_URL + 'api/job-experiences';

    constructor( protected http: HttpClient ) { }

    create( jobExperience: IJobExperience ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( jobExperience );
        return this.http
            .post<IJobExperience>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( jobExperience: IJobExperience ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( jobExperience );
        return this.http
            .put<IJobExperience>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<IJobExperience>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    findAll( candidateId: number ): Observable<EntityArrayResponseType> {
        return this.http
            .get<IJobExperience[]>( `${this.resourceUrl}/candidate/${candidateId}`, { observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<IJobExperience[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( jobExperience: IJobExperience ): IJobExperience {
        const copy: IJobExperience = Object.assign( {}, jobExperience, {
            createdDate:
            jobExperience.createdDate != null && jobExperience.createdDate.isValid() ? jobExperience.createdDate.toJSON() : null,
            lastModifiedDate:
            jobExperience.lastModifiedDate != null && jobExperience.lastModifiedDate.isValid()
                ? jobExperience.lastModifiedDate.toJSON()
                : null,
            startDate:
            jobExperience.startDate != null && jobExperience.startDate.isValid() ? jobExperience.startDate.format( DATE_FORMAT ) : null,
            endDate: jobExperience.endDate != null && jobExperience.endDate.isValid() ? jobExperience.endDate.format( DATE_FORMAT ) : null
        } );
        return copy;
    }

    protected convertDateFromServer( res: EntityResponseType ): EntityResponseType {
        if ( res.body ) {
            res.body.createdDate = res.body.createdDate != null ? moment( res.body.createdDate ) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment( res.body.lastModifiedDate ) : null;
            res.body.startDate = res.body.startDate != null ? moment( res.body.startDate ) : null;
            res.body.endDate = res.body.endDate != null ? moment( res.body.endDate ) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer( res: EntityArrayResponseType ): EntityArrayResponseType {
        if ( res.body ) {
            res.body.forEach(( jobExperience: IJobExperience ) => {
                jobExperience.createdDate = jobExperience.createdDate != null ? moment( jobExperience.createdDate ) : null;
                jobExperience.lastModifiedDate = jobExperience.lastModifiedDate != null ? moment( jobExperience.lastModifiedDate ) : null;
                jobExperience.startDate = jobExperience.startDate != null ? moment( jobExperience.startDate ) : null;
                jobExperience.endDate = jobExperience.endDate != null ? moment( jobExperience.endDate ) : null;
            } );
        }
        return res;
    }
}
