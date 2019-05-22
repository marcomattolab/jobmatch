import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable( { providedIn: 'root' } )
export class FileService {
    public resourceUrl = SERVER_API_URL + 'api';

    constructor( protected http: HttpClient ) { }

    public uploadUserProfileImg( image: File ): Observable<any> {
        const formData = new FormData();
        formData.append( 'file', image );
        return this.http.post( `${this.resourceUrl}/uploadUserProfileImg`, formData, { observe: 'response' } );
    }

    public getDownloadFileUrl( fileName: string ): string {
        return `${this.resourceUrl}/downloadFile/${fileName}`;
    }
    //  downloadFile( fileName: string ): Observable<any> {
    //  return this.http
    //      .get<any>( `${this.resourceUrl}/downloadFile/${fileName}`, { observe: 'response' } )
    //      .pipe( map(( res: any ) => res.file ) );
    // }
}
