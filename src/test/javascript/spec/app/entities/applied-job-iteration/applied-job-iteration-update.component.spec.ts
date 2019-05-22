/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { AppliedJobIterationUpdateComponent } from 'app/entities/applied-job-iteration/applied-job-iteration-update.component';
import { AppliedJobIterationService } from 'app/entities/applied-job-iteration/applied-job-iteration.service';
import { AppliedJobIteration } from 'app/shared/model/applied-job-iteration.model';

describe('Component Tests', () => {
    describe('AppliedJobIteration Management Update Component', () => {
        let comp: AppliedJobIterationUpdateComponent;
        let fixture: ComponentFixture<AppliedJobIterationUpdateComponent>;
        let service: AppliedJobIterationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [AppliedJobIterationUpdateComponent]
            })
                .overrideTemplate(AppliedJobIterationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AppliedJobIterationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AppliedJobIterationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AppliedJobIteration(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appliedJobIteration = entity;
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
                    const entity = new AppliedJobIteration();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appliedJobIteration = entity;
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
