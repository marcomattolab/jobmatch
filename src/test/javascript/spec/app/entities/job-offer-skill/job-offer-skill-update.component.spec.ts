/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { JobOfferSkillUpdateComponent } from 'app/entities/job-offer-skill/job-offer-skill-update.component';
import { JobOfferSkillService } from 'app/entities/job-offer-skill/job-offer-skill.service';
import { JobOfferSkill } from 'app/shared/model/job-offer-skill.model';

describe('Component Tests', () => {
    describe('JobOfferSkill Management Update Component', () => {
        let comp: JobOfferSkillUpdateComponent;
        let fixture: ComponentFixture<JobOfferSkillUpdateComponent>;
        let service: JobOfferSkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [JobOfferSkillUpdateComponent]
            })
                .overrideTemplate(JobOfferSkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobOfferSkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobOfferSkillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobOfferSkill(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobOfferSkill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobOfferSkill();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobOfferSkill = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
