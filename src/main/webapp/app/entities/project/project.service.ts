import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProject } from 'app/shared/model/project.model';

type EntityResponseType = HttpResponse<IProject>;
type EntityArrayResponseType = HttpResponse<IProject[]>;

@Injectable( { providedIn: 'root' } )
export class ProjectService {
    public resourceUrl = SERVER_API_URL + 'api/projects';

    constructor( protected http: HttpClient ) { }

    create( project: IProject ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( project );
        return this.http
            .post<IProject>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( project: IProject ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( project );
        return this.http
            .put<IProject>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<IProject>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<IProject[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( project: IProject ): IProject {
        const copy: IProject = Object.assign( {}, project, {
            createdDate: project.createdDate != null && project.createdDate.isValid() ? project.createdDate.toJSON() : null,
            lastModifiedDate:
            project.lastModifiedDate != null && project.lastModifiedDate.isValid() ? project.lastModifiedDate.toJSON() : null,
            startDate: project.startDate != null && project.startDate.isValid() ? project.startDate.format( DATE_FORMAT ) : null,
            endDate: project.endDate != null && project.endDate.isValid() ? project.endDate.format( DATE_FORMAT ) : null
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
            res.body.forEach(( project: IProject ) => {
                project.createdDate = project.createdDate != null ? moment( project.createdDate ) : null;
                project.lastModifiedDate = project.lastModifiedDate != null ? moment( project.lastModifiedDate ) : null;
                project.startDate = project.startDate != null ? moment( project.startDate ) : null;
                project.endDate = project.endDate != null ? moment( project.endDate ) : null;
                if ( project.description != null && project.description.length > 100 ) {
                    project.shortDescription = project.description.substring( 0, 100 ).concat( '...' );
                } else {
                    project.shortDescription = project.description;
                }
            } );
        }
        return res;
    }
}
