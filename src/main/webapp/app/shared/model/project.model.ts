import { Moment } from 'moment';
import { IJobOffer } from 'app/shared/model/job-offer.model';

export const enum ProjectStatus {
    ACTIVE = 'ACTIVE',
    ENDED = 'ENDED'
}

export interface IProject {
    id?: number;
    createdBy?: string;
    lastModifiedBy?: string;
    createdDate?: Moment;
    lastModifiedDate?: Moment;
    title?: string;
    description?: any;
    startDate?: Moment;
    status?: ProjectStatus;
    endDate?: Moment;
    jobOffers?: IJobOffer[];
    companyId?: number;
    sectorId?: number;
    shortDescription?: string;
    editAvailable?: boolean;
    deleteAvailable?: boolean;
}

export class Project implements IProject {
    constructor(
        public id?: number,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public createdDate?: Moment,
        public lastModifiedDate?: Moment,
        public title?: string,
        public description?: any,
        public startDate?: Moment,
        public status?: ProjectStatus,
        public endDate?: Moment,
        public jobOffers?: IJobOffer[],
        public companyId?: number,
        public sectorId?: number,
        public shortDescription?: string,
        public editAvailable?: boolean,
        public deleteAvailable?: boolean,
    ) {
        this.editAvailable = this.editAvailable || false;
        this.deleteAvailable = this.deleteAvailable || false;
    }
}
