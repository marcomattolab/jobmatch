/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JobmatchTestModule } from '../../../test.module';
import { CompanySectorUpdateComponent } from 'app/entities/company-sector/company-sector-update.component';
import { CompanySectorService } from 'app/entities/company-sector/company-sector.service';
import { CompanySector } from 'app/shared/model/company-sector.model';

describe('Component Tests', () => {
    describe('CompanySector Management Update Component', () => {
        let comp: CompanySectorUpdateComponent;
        let fixture: ComponentFixture<CompanySectorUpdateComponent>;
        let service: CompanySectorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JobmatchTestModule],
                declarations: [CompanySectorUpdateComponent]
            })
                .overrideTemplate(CompanySectorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompanySectorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanySectorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CompanySector(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.companySector = entity;
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
                    const entity = new CompanySector();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.companySector = entity;
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
