import { Pipe, PipeTransform } from '@angular/core';
import { AuthoritiesConstants } from '../constants/authorities.constants';
import { FileService } from '../file/file.service';

@Pipe( { name: 'imageProfile' } )
export class ImageProfilePipe implements PipeTransform {

    constructor( protected fileService: FileService ) { }

    transform( value: string, args: { role: string, gender?: string } ): string {
        let url = value;
        if ( !url ) {
            if ( args.role === AuthoritiesConstants.ROLE_CANDIDATE ) {
                if ( args.gender === 'F' ) {
                    url = 'content/images/woman-avatar.jpg';
                } else {
                    url = 'content/images/male-avatar.jpg';
                }
            } else if ( args.role === AuthoritiesConstants.ROLE_COMPANY ) {
                url = 'content/images/company-avatar.png';
            } else if ( args.role === AuthoritiesConstants.ROLE_SPONSORING_INSTUTUTION ) {
                url = 'content/images/company-avatar.png';
            }
        } else {
            url = this.fileService.getDownloadFileUrl( url );
        }
        return url;
    }
}
