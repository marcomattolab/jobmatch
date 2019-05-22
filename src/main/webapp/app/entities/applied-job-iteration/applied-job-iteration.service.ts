import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';

type EntityResponseType = HttpResponse<IAppliedJobIteration>;
type EntityArrayResponseType = HttpResponse<IAppliedJobIteration[]>;

@Injectable({ providedIn: 'root' })
export class AppliedJobIterationService {
    public resourceUrl = SERVER_API_URL + 'api/applied-job-iterations';

    constructor(protected http: HttpClient) {}

    create(appliedJobIteration: IAppliedJobIteration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(appliedJobIteration);
        return this.http
            .post<IAppliedJobIteration>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(appliedJobIteration: IAppliedJobIteration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(appliedJobIteration);
        return this.http
            .put<IAppliedJobIteration>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAppliedJobIteration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAppliedJobIteration[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(appliedJobIteration: IAppliedJobIteration): IAppliedJobIteration {
        const copy: IAppliedJobIteration = Object.assign({}, appliedJobIteration, {
            createdDate:
                appliedJobIteration.createdDate != null && appliedJobIteration.createdDate.isValid()
                    ? appliedJobIteration.createdDate.toJSON()
                    : null,
            lastModifiedDate:
                appliedJobIteration.lastModifiedDate != null && appliedJobIteration.lastModifiedDate.isValid()
                    ? appliedJobIteration.lastModifiedDate.toJSON()
                    : null,
            iterationDate:
                appliedJobIteration.iterationDate != null && appliedJobIteration.iterationDate.isValid()
                    ? appliedJobIteration.iterationDate.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
            res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
            res.body.iterationDate = res.body.iterationDate != null ? moment(res.body.iterationDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((appliedJobIteration: IAppliedJobIteration) => {
                appliedJobIteration.createdDate = appliedJobIteration.createdDate != null ? moment(appliedJobIteration.createdDate) : null;
                appliedJobIteration.lastModifiedDate =
                    appliedJobIteration.lastModifiedDate != null ? moment(appliedJobIteration.lastModifiedDate) : null;
                appliedJobIteration.iterationDate =
                    appliedJobIteration.iterationDate != null ? moment(appliedJobIteration.iterationDate) : null;
            });
        }
        return res;
    }
}
