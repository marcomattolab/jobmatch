import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDirectApplication } from 'app/shared/model/direct-application.model';

type EntityResponseType = HttpResponse<IDirectApplication>;
type EntityArrayResponseType = HttpResponse<IDirectApplication[]>;

@Injectable( { providedIn: 'root' } )
export class DirectApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/direct-applications';

    constructor( protected http: HttpClient ) { }

    create( directApplication: IDirectApplication ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( directApplication );
        return this.http
            .post<IDirectApplication>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( directApplication: IDirectApplication ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( directApplication );
        return this.http
            .put<IDirectApplication>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<IDirectApplication>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<IDirectApplication[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    isAlreadyApplied( candidateId: number, companyId: number ): Observable<boolean> {
        const options = createRequestOption( { candidateId, companyId } );
        return this.http.get<boolean>( `${this.resourceUrl}/alreadyApplied`, { params: options, observe: 'response' } )
            .pipe( map(( res: HttpResponse<boolean> ) => res.body ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( directApplication: IDirectApplication ): IDirectApplication {
        const copy: IDirectApplication = Object.assign( {}, directApplication, {
            createdDate:
            directApplication.createdDate != null && directApplication.createdDate.isValid()
                ? directApplication.createdDate.toJSON()
                : null,
            lastModifiedDate:
            directApplication.lastModifiedDate != null && directApplication.lastModifiedDate.isValid()
                ? directApplication.lastModifiedDate.toJSON()
                : null
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
            res.body.forEach(( directApplication: IDirectApplication ) => {
                directApplication.createdDate = directApplication.createdDate != null ? moment( directApplication.createdDate ) : null;
                directApplication.lastModifiedDate =
                    directApplication.lastModifiedDate != null ? moment( directApplication.lastModifiedDate ) : null;
            } );
        }
        return res;
    }
}
