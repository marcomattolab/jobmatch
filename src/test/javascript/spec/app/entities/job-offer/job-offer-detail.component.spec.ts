/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { JobOfferDetailComponent } from 'app/entities/job-offer/job-offer-detail.component';
import { JobOffer } from 'app/shared/model/job-offer.model';

describe('Component Tests', () => {
    describe('JobOffer Management Detail Component', () => {
        let comp: JobOfferDetailComponent;
        let fixture: ComponentFixture<JobOfferDetailComponent>;
        const route = ({ data: of({ jobOffer: new JobOffer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobOfferDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JobOfferDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobOfferDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jobOffer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
