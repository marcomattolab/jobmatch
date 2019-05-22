/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { DirectApplicationDetailComponent } from 'app/entities/direct-application/direct-application-detail.component';
import { DirectApplication } from 'app/shared/model/direct-application.model';

describe('Component Tests', () => {
    describe('DirectApplication Management Detail Component', () => {
        let comp: DirectApplicationDetailComponent;
        let fixture: ComponentFixture<DirectApplicationDetailComponent>;
        const route = ({ data: of({ directApplication: new DirectApplication(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [DirectApplicationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DirectApplicationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DirectApplicationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.directApplication).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
