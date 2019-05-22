/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { JobOfferSkillDetailComponent } from 'app/entities/job-offer-skill/job-offer-skill-detail.component';
import { JobOfferSkill } from 'app/shared/model/job-offer-skill.model';

describe('Component Tests', () => {
    describe('JobOfferSkill Management Detail Component', () => {
        let comp: JobOfferSkillDetailComponent;
        let fixture: ComponentFixture<JobOfferSkillDetailComponent>;
        const route = ({ data: of({ jobOfferSkill: new JobOfferSkill(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobOfferSkillDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JobOfferSkillDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobOfferSkillDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jobOfferSkill).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
