import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map, tap } from 'rxjs/operators';
import { JhiLanguageService } from 'ng-jhipster';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompanySector } from 'app/shared/model/company-sector.model';
import { UserSessionStorageService } from 'app/shared/user-session-storage/user-session-storage.service';

type EntityResponseType = HttpResponse<ICompanySector>;
type EntityArrayResponseType = HttpResponse<ICompanySector[]>;

@Injectable( { providedIn: 'root' } )
export class CompanySectorService {
    public resourceUrl = SERVER_API_URL + 'api/company-sectors';

    constructor( protected http: HttpClient, protected jhiLanguageService: JhiLanguageService, private userSessionStorage: UserSessionStorageService ) { }

    create( companySector: ICompanySector ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( companySector );
        return this.http
            .post<ICompanySector>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    update( companySector: ICompanySector ): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient( companySector );
        return this.http
            .put<ICompanySector>( this.resourceUrl, copy, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    find( id: number ): Observable<EntityResponseType> {
        const languageKey = this.jhiLanguageService.currentLang;
        return this.http
            .get<ICompanySector>( `${this.resourceUrl}/${languageKey}/${id}`, { observe: 'response' } )
            .pipe( map(( res: EntityResponseType ) => this.convertDateFromServer( res ) ) );
    }

    findDescriptionById( sectorId: number ): Observable<string> {
        const languageKey = this.jhiLanguageService.currentLang;
        const key = 'company-sectors-' + languageKey;
        const companySectorsRes = this.userSessionStorage.retrieve( key );
        let sector: ICompanySector = null;
        if ( companySectorsRes ) {
            const companySectors: ICompanySector[] = companySectorsRes.body;
            sector = companySectors.find(( cs: ICompanySector ) => cs.id === +sectorId );
            if ( sector ) {
                return of( sector.description );
            }
        } else {
            return this.find( sectorId ).pipe( map(( res: EntityResponseType ) => res.body.description ) );
        }
    }

    findAll(): Observable<EntityArrayResponseType> {
        const languageKey = this.jhiLanguageService.currentLang;
        const key = 'company-sectors-' + languageKey;
        const companySectors = this.userSessionStorage.retrieve( key );
        if ( companySectors ) {
            return of( companySectors );
        }
        return this.http
            .get<ICompanySector[]>( `${this.resourceUrl}/all/${languageKey}`, { observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ),
            tap(( res: EntityArrayResponseType ) => this.userSessionStorage.store( key, res ) ) );
    }

    query( req?: any ): Observable<EntityArrayResponseType> {
        const options = createRequestOption( req );
        return this.http
            .get<ICompanySector[]>( this.resourceUrl, { params: options, observe: 'response' } )
            .pipe( map(( res: EntityArrayResponseType ) => this.convertDateArrayFromServer( res ) ) );
    }

    delete( id: number ): Observable<HttpResponse<any>> {
        return this.http.delete<any>( `${this.resourceUrl}/${id}`, { observe: 'response' } );
    }

    protected convertDateFromClient( companySector: ICompanySector ): ICompanySector {
        const copy: ICompanySector = Object.assign( {}, companySector, {
            createdDate:
            companySector.createdDate != null && companySector.createdDate.isValid() ? companySector.createdDate.toJSON() : null,
            lastModifiedDate:
            companySector.lastModifiedDate != null && companySector.lastModifiedDate.isValid()
                ? companySector.lastModifiedDate.toJSON()
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
            res.body.forEach(( companySector: ICompanySector ) => {
                companySector.createdDate = companySector.createdDate != null ? moment( companySector.createdDate ) : null;
                companySector.lastModifiedDate = companySector.lastModifiedDate != null ? moment( companySector.lastModifiedDate ) : null;
            } );
        }
        return res;
    }
}
