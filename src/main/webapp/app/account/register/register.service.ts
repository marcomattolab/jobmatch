import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthoritiesConstants } from 'app/shared/constants/authorities.constants';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';

const API_URL = SERVER_API_URL + 'api/';

@Injectable( { providedIn: 'root' } )
export class Register {
    constructor( private http: HttpClient ) { }

    save( account: any ): Observable<any> {
        let serviceName = 'register';
        if ( account.roleAccount === AuthoritiesConstants.ROLE_CANDIDATE ) {
            serviceName = 'register-candidate';
        } else if ( account.roleAccount === AuthoritiesConstants.ROLE_COMPANY ) {
            serviceName = 'register-company';
        } else if ( account.roleAccount === AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION ) {
            serviceName = 'register-institution';
        }
        const accountCopy = this.convertDateFromClient( account );
        return this.http.post( API_URL + serviceName, accountCopy );
    }

    protected convertDateFromClient( account: any ): any {
        const copy: any = Object.assign( {}, account, {
            foundationDate:
            account.foundationDate != null && account.foundationDate.isValid()
                ? account.foundationDate.format( DATE_FORMAT )
                : null
        } );
        return copy;
    }
}
