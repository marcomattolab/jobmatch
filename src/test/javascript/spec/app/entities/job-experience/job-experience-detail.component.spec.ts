/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { JobExperienceDetailComponent } from 'app/entities/job-experience/job-experience-detail.component';
import { JobExperience } from 'app/shared/model/job-experience.model';

describe('Component Tests', () => {
    describe('JobExperience Management Detail Component', () => {
        let comp: JobExperienceDetailComponent;
        let fixture: ComponentFixture<JobExperienceDetailComponent>;
        const route = ({ data: of({ jobExperience: new JobExperience(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobExperienceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JobExperienceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobExperienceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jobExperience).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
