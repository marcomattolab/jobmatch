/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { SkillTagUpdateComponent } from 'app/entities/skill-tag/skill-tag-update.component';
import { SkillTagService } from 'app/entities/skill-tag/skill-tag.service';
import { SkillTag } from 'app/shared/model/skill-tag.model';

describe('Component Tests', () => {
    describe('SkillTag Management Update Component', () => {
        let comp: SkillTagUpdateComponent;
        let fixture: ComponentFixture<SkillTagUpdateComponent>;
        let service: SkillTagService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [SkillTagUpdateComponent]
            })
                .overrideTemplate(SkillTagUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SkillTagUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SkillTagService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SkillTag(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.skillTag = entity;
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
                    const entity = new SkillTag();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.skillTag = entity;
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
