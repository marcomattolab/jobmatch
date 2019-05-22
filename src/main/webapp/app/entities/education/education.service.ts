import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IEducation } from 'app/shared/model/education.model';
import * as moment from 'moment';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IEducation>;
type EntityArrayResponseType = HttpResponse<IEducation[]>;

@Injectable({ providedIn: 'root' })
export class EducationService {
    public resourceUrl = SERVER_API_URL + 'api/educations';

    constructor(protected http: HttpClient) { }

    create(education: IEducation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(education);
        return this.http
            .post<IEducation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(education: IEducation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(education);
        return this.http
            .put<IEducation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEducation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findAll(candidateId: number): Observable<EntityArrayResponseType> {
        return this.http
            .get<IEducation[]>(`${this.resourceUrl}/candidate/${candidateId}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEducation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(education: IEducation): IEducation {
        const copy: IEducation = Object.assign({}, education, {
            createdDate: education.createdDate != null && education.createdDate.isValid() ? education.createdDate.toJSON() : null,
            lastModifiedDate:
                education.lastModifiedDate != null && education.lastModifiedDate.isValid() ? education.lastModifiedDate.toJSON() : null,
            startDate: education.startDate != null && education.startDate.isValid() ? education.startDate.format(DATE_FORMAT) : null,
            endDate: education.endDate != null && education.endDate.isValid() ? education.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((education: IEducation) => {
                education.createdDate = education.createdDate != null ? moment(education.createdDate) : null;
                education.lastModifiedDate = education.lastModifiedDate != null ? moment(education.lastModifiedDate) : null;
                education.startDate = education.startDate != null ? moment(education.startDate) : null;
                education.endDate = education.endDate != null ? moment(education.endDate) : null;
            });
        }
        return res;
    }
}
