/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { CompanySkillDetailComponent } from 'app/entities/company-skill/company-skill-detail.component';
import { CompanySkill } from 'app/shared/model/company-skill.model';

describe('Component Tests', () => {
    describe('CompanySkill Management Detail Component', () => {
        let comp: CompanySkillDetailComponent;
        let fixture: ComponentFixture<CompanySkillDetailComponent>;
        const route = ({ data: of({ companySkill: new CompanySkill(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [CompanySkillDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompanySkillDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompanySkillDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.companySkill).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
