/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { DirectApplicationUpdateComponent } from 'app/entities/direct-application/direct-application-update.component';
import { DirectApplicationService } from 'app/entities/direct-application/direct-application.service';
import { DirectApplication } from 'app/shared/model/direct-application.model';

describe('Component Tests', () => {
    describe('DirectApplication Management Update Component', () => {
        let comp: DirectApplicationUpdateComponent;
        let fixture: ComponentFixture<DirectApplicationUpdateComponent>;
        let service: DirectApplicationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [DirectApplicationUpdateComponent]
            })
                .overrideTemplate(DirectApplicationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DirectApplicationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DirectApplicationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DirectApplication(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.directApplication = entity;
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
                    const entity = new DirectApplication();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.directApplication = entity;
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
