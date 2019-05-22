import { Moment } from 'moment';
import { Country } from './candidate.model';

export const enum IstituitionSectorType {
    ICT_IT = 'ICT_IT',
    CHARITY = 'CHARITY',
    GOVERNAMENT = 'GOVERNAMENT',
    LAW = 'LAW',
    PUBLIC = 'PUBLIC',
    PRIVATE = 'PRIVATE',
    OTHER = 'OTHER'
}

export const enum IstituitionType {
    PUBLIC = 'PUBLIC',
    PRIVATE = 'PRIVATE',
    OMG = 'OMG',
    RESEARCH = 'RESEARCH',
    OTHER = 'OTHER'
}

export interface ISponsoringInstitution {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    istituitionName?: string;
    istituitionDescription?: any;
    istituitionSector?: IstituitionSectorType;
    istituitionType?: IstituitionType;
    streetAddress?: string;
    foundationDate?: Moment;
    vatNumber?: string;
    email?: string;
    phone?: string;
    mobilePhone?: string;
    country?: Country;
    region?: string;
    province?: string;
    city?: string;
    cap?: string;
    urlSite?: string;
    enabled?: boolean;
    userId?: number;
    imageUrl?: string;
    editAvailable?: boolean;
    deleteAvailable?: boolean;
}

export class SponsoringInstitution implements ISponsoringInstitution {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public istituitionName?: string,
        public istituitionDescription?: any,
        public istituitionSector?: IstituitionSectorType,
        public istituitionType?: IstituitionType,
        public streetAddress?: string,
        public foundationDate?: Moment,
        public vatNumber?: string,
        public email?: string,
        public phone?: string,
        public mobilePhone?: string,
        public country?: Country,
        public region?: string,
        public province?: string,
        public city?: string,
        public cap?: string,
        public urlSite?: string,
        public enabled?: boolean,
        public userId?: number,
        public imageUrl?: string,
        public editAvailable?: boolean,
        public deleteAvailable?: boolean
    ) {
        this.enabled = this.enabled || false;
        this.editAvailable = this.editAvailable || false;
        this.deleteAvailable = this.deleteAvailable || false;
    }
}
