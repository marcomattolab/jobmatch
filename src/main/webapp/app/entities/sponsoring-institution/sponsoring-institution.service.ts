import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';

type EntityResponseType = HttpResponse<ISponsoringInstitution>;
type EntityArrayResponseType = HttpResponse<ISponsoringInstitution[]>;

@Injectable( { providedIn: 'root' } )
export class SponsoringInstitutionService {
    public resourceUrl = SERVER_API_URL + 'api/sponsoring-institutions';

    constructor( protected http: HttpClient ) { }

    create( sponsoringInstitution: ISponsoringInstitution ): Observable<EntityResponseType> {
        const toSend = this.prepareBeforeSave(sponsoringInstitution);
        return this.http
            .post<ISponsoringInstitution>( this.resourceUrl, toSend, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( sponsoringInstitution: ISponsoringInstitution ): Observable<EntityResponseType> {
        const toSend = this.prepareBeforeSave(sponsoringInstitution);
        return this.http
            .put<ISponsoringInstitution>( this.resourceUrl, toSend, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<ISponsoringInstitution>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    findByUser(): Observable<EntityResponseType> {
        return this.http
            .get<ISponsoringInstitution>( `${this.resourceUrl}/currentUser`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<ISponsoringInstitution[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected prepareBeforeSave( sponsoringInstitution: ISponsoringInstitution ): ISponsoringInstitution {
        const copy = this.convertDateFromClient(sponsoringInstitution);

        if (copy.urlSite && copy.urlSite === '') {
            copy.urlSite = null;
        }

        return copy;
    }

    protected convertDateFromClient( sponsoringInstitution: ISponsoringInstitution ): ISponsoringInstitution {
        const copy: ISponsoringInstitution = Object.assign( {}, sponsoringInstitution, {
            createdDate:
            sponsoringInstitution.createdDate != null && sponsoringInstitution.createdDate.isValid()
                ? sponsoringInstitution.createdDate.toJSON()
                : null,
            lastModifiedDate:
            sponsoringInstitution.lastModifiedDate != null && sponsoringInstitution.lastModifiedDate.isValid()
                ? sponsoringInstitution.lastModifiedDate.toJSON()
                : null,
            foundationDate:
            sponsoringInstitution.foundationDate != null && sponsoringInstitution.foundationDate.isValid()
                ? sponsoringInstitution.foundationDate.format( DATE_FORMAT )
                : null
        } );
        return copy;
    }

    protected convertDateFromServer( res: EntityResponseType ): EntityResponseType {
        if ( res.body ) {
            res.body.createdDate = res.body.createdDate != null ? moment( res.body.createdDate ) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment( res.body.lastModifiedDate ) : null;
            res.body.foundationDate = res.body.foundationDate != null ? moment( res.body.foundationDate ) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer( res: EntityArrayResponseType ): EntityArrayResponseType {
        if ( res.body ) {
            res.body.forEach(( sponsoringInstitution: ISponsoringInstitution ) => {
                sponsoringInstitution.createdDate =
                    sponsoringInstitution.createdDate != null ? moment( sponsoringInstitution.createdDate ) : null;
                sponsoringInstitution.lastModifiedDate =
                    sponsoringInstitution.lastModifiedDate != null ? moment( sponsoringInstitution.lastModifiedDate ) : null;
                sponsoringInstitution.foundationDate =
                    sponsoringInstitution.foundationDate != null ? moment( sponsoringInstitution.foundationDate ) : null;
            } );
        }
        return res;
    }
}
