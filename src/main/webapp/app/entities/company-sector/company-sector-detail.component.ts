import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanySector } from 'app/shared/model/company-sector.model';

@Component({
    selector: 'jhi-company-sector-detail',
    templateUrl: './company-sector-detail.component.html'
})
export class CompanySectorDetailComponent implements OnInit {
    companySector: ICompanySector;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ companySector }) => {
            this.companySector = companySector;
        });
    }

    previousState() {
        window.history.back();
    }
}
