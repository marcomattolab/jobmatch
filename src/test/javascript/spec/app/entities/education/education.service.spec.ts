/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EducationService } from 'app/entities/education/education.service';
import { IEducation, Education } from 'app/shared/model/education.model';

describe('Service Tests', () => {
    describe('Education Service', () => {
        let injector: TestBed;
        let service: EducationService;
        let httpMock: HttpTestingController;
        let elemDefault: IEducation;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(EducationService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Education(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Education', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Education(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Education', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        schoolName: 'BBBBBB',
                        type: 'BBBBBB',
                        fieldOfStudy: 'BBBBBB',
                        description: 'BBBBBB',
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        graduaded: true,
                        current: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Education', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        schoolName: 'BBBBBB',
                        type: 'BBBBBB',
                        fieldOfStudy: 'BBBBBB',
                        description: 'BBBBBB',
                        startDate: currentDate.format(DATE_FORMAT),
                        endDate: currentDate.format(DATE_FORMAT),
                        graduaded: true,
                        current: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        startDate: currentDate,
                        endDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Education', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
