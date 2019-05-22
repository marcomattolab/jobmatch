/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { CompanySectorDetailComponent } from 'app/entities/company-sector/company-sector-detail.component';
import { CompanySector } from 'app/shared/model/company-sector.model';

describe('Component Tests', () => {
    describe('CompanySector Management Detail Component', () => {
        let comp: CompanySectorDetailComponent;
        let fixture: ComponentFixture<CompanySectorDetailComponent>;
        const route = ({ data: of({ companySector: new CompanySector(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [CompanySectorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompanySectorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompanySectorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.companySector).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
