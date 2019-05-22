import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocument } from 'app/shared/model/document.model';

type EntityResponseType = HttpResponse<IDocument>;
type EntityArrayResponseType = HttpResponse<IDocument[]>;

@Injectable( { providedIn: 'root' } )
export class DocumentService {
    public resourceUrl = SERVER_API_URL + 'api/documents';

    constructor( protected http: HttpClient ) { }

    create( document: IDocument ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( document );
        return this.http
            .post<IDocument>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( document: IDocument ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( document );
        return this.http
            .put<IDocument>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<IDocument>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    findAll( candidateId: number ): Observable<EntityArrayResponseType> {
        return this.http
            .get<IDocument[]>( `${this.resourceUrl}/candidate/${candidateId}`, { observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<IDocument[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( document: IDocument ): IDocument {
        const copy: IDocument = Object.assign( {}, document, {
            createdDate: document.createdDate != null && document.createdDate.isValid() ? document.createdDate.toJSON() : null,
            lastModifiedDate:
            document.lastModifiedDate != null && document.lastModifiedDate.isValid() ? document.lastModifiedDate.toJSON() : null
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
            res.body.forEach(( document: IDocument ) => {
                document.createdDate = document.createdDate != null ? moment( document.createdDate ) : null;
                document.lastModifiedDate = document.lastModifiedDate != null ? moment( document.lastModifiedDate ) : null;
            } );
        }
        return res;
    }
}
