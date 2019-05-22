/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { CompanySkillUpdateComponent } from 'app/entities/company-skill/company-skill-update.component';
import { CompanySkillService } from 'app/entities/company-skill/company-skill.service';
import { CompanySkill } from 'app/shared/model/company-skill.model';

describe('Component Tests', () => {
    describe('CompanySkill Management Update Component', () => {
        let comp: CompanySkillUpdateComponent;
        let fixture: ComponentFixture<CompanySkillUpdateComponent>;
        let service: CompanySkillService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [CompanySkillUpdateComponent]
            })
                .overrideTemplate(CompanySkillUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompanySkillUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanySkillService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CompanySkill(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.companySkill = entity;
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
                    const entity = new CompanySkill();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.companySkill = entity;
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
