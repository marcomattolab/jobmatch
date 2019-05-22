import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISponsoringInstitution } from 'app/shared/model/sponsoring-institution.model';

@Component({
    selector: 'jhi-sponsoring-institution-detail',
    templateUrl: './sponsoring-institution-detail.component.html'
})
export class SponsoringInstitutionDetailComponent implements OnInit {
    sponsoringInstitution: ISponsoringInstitution;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sponsoringInstitution }) => {
            this.sponsoringInstitution = sponsoringInstitution;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
