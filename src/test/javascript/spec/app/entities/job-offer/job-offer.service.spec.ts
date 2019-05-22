/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JobOfferService } from 'app/entities/job-offer/job-offer.service';
import { IJobOffer, JobOffer, EmploymentType, SeniorityLevel, JobOfferStatus, JobOfferType } from 'app/shared/model/job-offer.model';
import { Country, } from 'app/shared/model/candidate.model';

describe('Service Tests', () => {
    describe('JobOffer Service', () => {
        let injector: TestBed;
        let service: JobOfferService;
        let httpMock: HttpTestingController;
        let elemDefault: IJobOffer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(JobOfferService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new JobOffer(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                JobOfferType.JOB,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Country.IT,
                EmploymentType.FULL_TIME,
                SeniorityLevel.BEGINNER,
                'AAAAAAA',
                JobOfferStatus.DRAFT,
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT)
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

            it('should create a JobOffer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        startDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new JobOffer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a JobOffer', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        jobTitle: 'BBBBBB',
                        jobShortDescription: 'BBBBBB',
                        jobDescription: 'BBBBBB',
                        responsibilitiesDescription: 'BBBBBB',
                        experiencesDescription: 'BBBBBB',
                        attributesDescription: 'BBBBBB',
                        jobFunctions: 'BBBBBB',
                        jobCity: 'BBBBBB',
                        jobCountry: 'BBBBBB',
                        employmentType: 'BBBBBB',
                        seniorityLevel: 'BBBBBB',
                        salaryOffered: 'BBBBBB',
                        status: 'BBBBBB',
                        enabled: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        startDate: currentDate
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

            it('should return a list of JobOffer', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdBy: 'BBBBBB',
                        lastModifiedBy: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastModifiedDate: currentDate.format(DATE_TIME_FORMAT),
                        startDate: currentDate.format(DATE_FORMAT),
                        jobTitle: 'BBBBBB',
                        jobShortDescription: 'BBBBBB',
                        jobDescription: 'BBBBBB',
                        responsibilitiesDescription: 'BBBBBB',
                        experiencesDescription: 'BBBBBB',
                        attributesDescription: 'BBBBBB',
                        jobFunctions: 'BBBBBB',
                        jobCity: 'BBBBBB',
                        jobCountry: 'BBBBBB',
                        employmentType: 'BBBBBB',
                        seniorityLevel: 'BBBBBB',
                        salaryOffered: 'BBBBBB',
                        status: 'BBBBBB',
                        enabled: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastModifiedDate: currentDate,
                        startDate: currentDate
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

            it('should delete a JobOffer', async () => {
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
