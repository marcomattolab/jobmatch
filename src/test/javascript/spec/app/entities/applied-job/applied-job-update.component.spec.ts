/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { AppliedJobUpdateComponent } from 'app/entities/applied-job/applied-job-update.component';
import { AppliedJobService } from 'app/entities/applied-job/applied-job.service';
import { AppliedJob } from 'app/shared/model/applied-job.model';

describe('Component Tests', () => {
    describe('AppliedJob Management Update Component', () => {
        let comp: AppliedJobUpdateComponent;
        let fixture: ComponentFixture<AppliedJobUpdateComponent>;
        let service: AppliedJobService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [AppliedJobUpdateComponent]
            })
                .overrideTemplate(AppliedJobUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AppliedJobUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppliedJobService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AppliedJob(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appliedJob = entity;
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
                    const entity = new AppliedJob();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appliedJob = entity;
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
