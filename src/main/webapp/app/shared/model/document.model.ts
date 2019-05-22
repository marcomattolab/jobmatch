import { Moment } from 'moment';

export const enum DocumentType {
    CV = 'CV',
    OTHER = 'OTHER'
}

export interface IDocument {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    documentType?: DocumentType;
    name?: string;
    contentContentType?: string;
    content?: any;
    description?: string;
    candidateId?: number;
}

export class Document implements IDocument {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public documentType?: DocumentType,
        public name?: string,
        public contentContentType?: string,
        public content?: any,
        public description?: string,
        public candidateId?: number
    ) {}
}
