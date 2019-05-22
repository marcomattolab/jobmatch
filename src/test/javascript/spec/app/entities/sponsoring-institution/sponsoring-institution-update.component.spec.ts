/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { SponsoringInstitutionUpdateComponent } from 'app/entities/sponsoring-institution/sponsoring-institution-update.component';
import { SponsoringInstitutionService } from 'app/entities/sponsoring-institution/sponsoring-institution.service';
import { SponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';

describe('Component Tests', () => {
    describe('SponsoringInstitution Management Update Component', () => {
        let comp: SponsoringInstitutionUpdateComponent;
        let fixture: ComponentFixture<SponsoringInstitutionUpdateComponent>;
        let service: SponsoringInstitutionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [SponsoringInstitutionUpdateComponent]
            })
                .overrideTemplate(SponsoringInstitutionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SponsoringInstitutionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SponsoringInstitutionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SponsoringInstitution(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sponsoringInstitution = entity;
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
                    const entity = new SponsoringInstitution();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sponsoringInstitution = entity;
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
