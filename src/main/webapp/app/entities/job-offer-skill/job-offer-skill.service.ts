import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobOfferSkill } from 'app/shared/model/job-offer-skill.model';

type EntityResponseType = HttpResponse<IJobOfferSkill>;
type EntityArrayResponseType = HttpResponse<IJobOfferSkill[]>;

@Injectable({ providedIn: 'root' })
export class JobOfferSkillService {
    public resourceUrl = SERVER_API_URL + 'api/job-offer-skills';

    constructor(protected http: HttpClient) {}

    create(jobOfferSkill: IJobOfferSkill): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(jobOfferSkill);
        return this.http
            .post<IJobOfferSkill>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(jobOfferSkill: IJobOfferSkill): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(jobOfferSkill);
        return this.http
            .put<IJobOfferSkill>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IJobOfferSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJobOfferSkill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(jobOfferSkill: IJobOfferSkill): IJobOfferSkill {
        const copy: IJobOfferSkill = Object.assign({}, jobOfferSkill, {
            createdDate:
                jobOfferSkill.createdDate != null && jobOfferSkill.createdDate.isValid() ? jobOfferSkill.createdDate.toJSON() : null,
            lastModifiedDate:
                jobOfferSkill.lastModifiedDate != null && jobOfferSkill.lastModifiedDate.isValid()
                    ? jobOfferSkill.lastModifiedDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((jobOfferSkill: IJobOfferSkill) => {
                jobOfferSkill.createdDate = jobOfferSkill.createdDate != null ? moment(jobOfferSkill.createdDate) : null;
                jobOfferSkill.lastModifiedDate = jobOfferSkill.lastModifiedDate != null ? moment(jobOfferSkill.lastModifiedDate) : null;
            });
        }
        return res;
    }
}
