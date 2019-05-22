import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkill } from 'app/shared/model/skill.model';

type EntityResponseType = HttpResponse<ISkill>;
type EntityArrayResponseType = HttpResponse<ISkill[]>;

@Injectable( { providedIn: 'root' } )
export class SkillService {
    public resourceUrl = SERVER_API_URL + 'api/skills';

    constructor( protected http: HttpClient ) { }

    create( skill: ISkill ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( skill );
        return this.http
            .post<ISkill>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( skill: ISkill ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( skill );
        return this.http
            .put<ISkill>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        return this.http
            .get<ISkill>( `${this.resourceUrl}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    findAll( candidateId: number ): Observable<EntityArrayResponseType> {
        return this.http
            .get<ISkill[]>( `${this.resourceUrl}/candidate/${candidateId}`, { observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<ISkill[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( skill: ISkill ): ISkill {
        const copy: ISkill = Object.assign( {}, skill, {
            createdDate: skill.createdDate != null && skill.createdDate.isValid() ? skill.createdDate.toJSON() : null,
            lastModifiedDate: skill.lastModifiedDate != null && skill.lastModifiedDate.isValid() ? skill.lastModifiedDate.toJSON() : null
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
            res.body.forEach(( skill: ISkill ) => {
                skill.createdDate = skill.createdDate != null ? moment( skill.createdDate ) : null;
                skill.lastModifiedDate = skill.lastModifiedDate != null ? moment( skill.lastModifiedDate ) : null;
            } );
        }
        return res;
    }
}
