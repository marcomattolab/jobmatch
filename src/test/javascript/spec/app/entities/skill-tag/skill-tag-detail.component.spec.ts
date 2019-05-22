/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { SkillTagDetailComponent } from 'app/entities/skill-tag/skill-tag-detail.component';
import { SkillTag } from 'app/shared/model/skill-tag.model';

describe('Component Tests', () => {
    describe('SkillTag Management Detail Component', () => {
        let comp: SkillTagDetailComponent;
        let fixture: ComponentFixture<SkillTagDetailComponent>;
        const route = ({ data: of({ skillTag: new SkillTag(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [SkillTagDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SkillTagDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SkillTagDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.skillTag).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
